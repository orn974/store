package com.store.web;

import com.store.model.Product;
import org.springframework.boot.env.ConfigTreePropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class StoreController {
    public Long tempId;
    RestTemplate restTemplate;
    {restTemplate = new RestTemplate();}
    final String ROOT_URL = "http://localhost:8081/";

    @GetMapping ("/getController")
    public String getContollerMethod (Model model){
    ResponseEntity<List> stores = restTemplate.getForEntity(ROOT_URL+"products", List.class);
        List storeList = stores.getBody();
        model.addAttribute("storesListWeb", storeList);
        return "GetBase";
    }

    @GetMapping("/postProduct")
    public String postProductWeb(Model model){
        model.addAttribute("newProduct", new Product());
        return "PostBase";}
    @PostMapping("/postProduct")
    public String postProductWeb (Product newProduct) throws Exception{
        System.out.println("Product 1 " + newProduct);
        restTemplate.postForEntity(ROOT_URL+"post", newProduct, Product.class);
        System.out.println("Product 2 " + newProduct);
        return "redirect:/getController";
    }

    @GetMapping("/putProduct")
    public String putProductWeb (Model model){
        ResponseEntity<Product> product = restTemplate.getForEntity(ROOT_URL + "getOne/2", Product.class);
        System.out.println("productPut" + product.getBody());
        tempId = Objects.requireNonNull(product.getBody()).getStoreId();
        System.out.println("TempID "+ tempId);
        model.addAttribute("productPut", product.getBody());
        return "PutBase";
    }
    @PostMapping("/putProductWeb")
    public String putProductWeb (Product productPut) throws Exception{
        System.out.println("owerwriteProduct 1 " + productPut);
        //productPut.setStoreId(tempId);
        System.out.println("IDNEW"+productPut.getStoreId());
        restTemplate.put(ROOT_URL + "put", productPut, Product.class);
        System.out.println("owerwriteProduct 2 " + productPut);
        return "redirect:/getController";
    }

    @GetMapping("/delProduct")
    public String delProduct (Model model){
        return "DelBase";
    }
    @DeleteMapping("/delProduct")
    public String delProduct (@PathVariable (value = "id") Long id){
        restTemplate = new RestTemplate();
        restTemplate.delete(ROOT_URL+"delete", id);
        return "redirect:/getController";
    }
}
