package com.store.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.model.Product;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class StoreController {

    RestTemplate restTemplate;
    {restTemplate = new RestTemplate();}
    final String ROOT_URL = "http://localhost:8020/rest-app-0.0.1-SNAPSHOT/";

    @GetMapping("/")
    public String start(){
        return "Start";
    }

    @GetMapping ("/getController")
    public String getContollerMethod (Model model){
    ResponseEntity<List> stores = restTemplate.getForEntity(ROOT_URL+"products", List.class);
        List storeList = stores.getBody();
        model.addAttribute("storesListWeb", storeList);
        model.addAttribute("filename", new String("ProductFile"));
        return "GetBase";
    }

    @GetMapping("/postProduct")
    public String postProductWeb(Model model){
        model.addAttribute("newProduct", new Product());
        return "PostBase";}

    @PostMapping("/postProduct")
    public String postProductWeb (Product newProduct, Model model) throws Exception{
        System.out.println("Product 1 " + newProduct);
        restTemplate.postForEntity(ROOT_URL+"post", newProduct, Product.class);
        System.out.println("Product 2 " + newProduct);
        return "redirect:/getController";
    }

    @GetMapping("/putProduct/{storeId}")
    public String putProductWeb (@PathVariable Long storeId, Model model){
        ResponseEntity<Product> product = restTemplate.getForEntity(ROOT_URL + "getOne/" + storeId, Product.class);
        System.out.println("productPut" + product.getBody());
        model.addAttribute("productPut", product.getBody());
        return "PutBase";
    }
    @PostMapping("/putProductWeb")
    public String putProductWeb (Product productPut,Model model) throws Exception{
        restTemplate.put(ROOT_URL + "put", productPut, Product.class);
        return "redirect:/getController";
    }

    @GetMapping("/delProduct/{storeId}")
    public String delProduct (@PathVariable Long storeId){
        restTemplate.delete(ROOT_URL+"delete/" + storeId);
        return "redirect:/getController";
    }
    @PostMapping ("/save")
    public ResponseEntity<InputStreamResource> saveFile (String filename, Model model) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        //SimpleDateFormat df = new SimpleDateFormat("'date':yyyy-MM-dd");
        //df.applyPattern("'date':yyyy-MM-dd");
        //objectMapper.setDateFormat(df);
        objectMapper.registerModule(new JavaTimeModule());
        ResponseEntity<List> stores = restTemplate.getForEntity(ROOT_URL+"products", List.class);
        List<Product> storesList = objectMapper.convertValue(stores.getBody(), new TypeReference<List<Product>>() { });
        ByteArrayInputStream in = SaveFileExcel.tutorialsToExcel(storesList);
        InputStreamResource file = new InputStreamResource(in);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ filename+".xls")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

    }
    @GetMapping ("/load")
    public String loadFile () {

        return "redirect:/getController";
    }
    @PostMapping ("/load")
    public String loadFile (@RequestParam("LoadFileName") MultipartFile LoadFileName) throws IOException {
        Workbook workbook = new XSSFWorkbook(LoadFileName.getInputStream());

            Sheet sheet = workbook.sheetIterator().next();
            processSheet(sheet);
            return "redirect:/getController";
        }

        private Workbook loadWorkbook (String filename) throws IOException {
            var extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            var file = new FileInputStream(new File(filename));
            switch (extension) {
                case "xls":
                    // old format
                    return new HSSFWorkbook(file);
                case "xlsx":
                    // new format
                    return new XSSFWorkbook(file);
                default:
                    throw new RuntimeException("Unknown Excel file extension: " + extension);
            }
        }
        private void processSheet (Sheet sheet){
            System.out.println("Sheet: " + sheet.getSheetName());
            var data = new HashMap<Integer, List<Object>>();
            var iterator = sheet.rowIterator();
            for (var rowIndex = 0; iterator.hasNext(); rowIndex++) {
                var row = iterator.next();
                processRow(data, rowIndex, row);
            }
            System.out.println("Sheet data:");
           // System.out.println(data);
            int i = 0;
            ArrayList arrayProduct = new ArrayList();
            for (List<Object> list: data.values()) {
                System.out.println("List = "+list);
                if (i > 0){
                Product product = new Product();
                //product.setStoreId(Long.parseLong(list.get(0).toString()));
                product.setProductName(list.get(1).toString());
                product.setCost(new BigDecimal (list.get(2).toString()));
                product.setProductDate(LocalDate.parse(list.get(3).toString()));
                System.out.println("Product = "+product);
                    arrayProduct.add(product);
                }
                i++;

            }
            restTemplate.postForEntity(ROOT_URL + "postall",arrayProduct, ArrayList.class);
        }
        private void processRow (HashMap < Integer, List < Object >> data,int rowIndex, Row row){
            data.put(rowIndex, new ArrayList<>());
            for (var cell : row) {
                processCell(cell, data.get(rowIndex));
            }
        }
        private void processCell (Cell cell, List < Object > dataRow){
            switch (cell.getCellType()) {
                case STRING:
                    dataRow.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        dataRow.add(cell.getDateCellValue()); //getLocalDateTimeCellValue()
                    } else {
                        dataRow.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
                    }
                    break;
                case BOOLEAN:
                    dataRow.add(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    dataRow.add(cell.getCellFormula());
                    break;
                default:
                    dataRow.add(" ");
            }

            //modelMap.addAttribute("LoadFileName", LoadFileName);

        }

}
