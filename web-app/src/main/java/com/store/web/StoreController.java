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
}
