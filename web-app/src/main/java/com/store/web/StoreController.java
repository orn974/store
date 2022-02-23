package com.store.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.model.Product;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {

    RestTemplate restTemplate;
    {restTemplate = new RestTemplate();}
    final String ROOT_URL = "http://localhost:8081/";

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
    public void loadFile (String LoadFileName, Model model) throws IOException {
        restTemplate.postForEntity(ROOT_URL + "postall",LoadFileExcel.readFile(LoadFileName), ArrayList.class);
    }

}
