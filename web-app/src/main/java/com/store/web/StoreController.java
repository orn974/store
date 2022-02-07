package com.store.web;

import com.store.model.Product;
import org.springframework.boot.env.ConfigTreePropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
public class StoreController {
    RestTemplate restTemplate;
    {restTemplate = new RestTemplate();}
    final String ROOT_URL = "http://localhost:8081/";

    @GetMapping ("/getController")
    public String getContollerMethod (Model model){
    restTemplate = new RestTemplate();
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
        //restTemplate = new RestTemplate();
        restTemplate.postForEntity(ROOT_URL+"post", newProduct, Product.class);
        System.out.println("Product 2 " + newProduct);
        return "redirect:/getController";
    }

    @GetMapping("/putProduct")
    public String putProductWeb (Model model){

//        ResponseEntity<Optional> prodactOptional = restTemplate.getForEntity(ROOT_URL + "/getOne/2", Optional.class);
//        Optional<Product> productProduct= (Optional<Product>) prodactOptional.getBody().get();
        ResponseEntity<Product> product = restTemplate.getForEntity(ROOT_URL + "/getOne/2", Product.class);
       // Optional<Product> productProduct= (Optional<Product>) prodactOptional.getBody().get();
        System.out.println("getproduct" + product);
        model.addAttribute("getProduct", product);
        return "PutBase";
    }
    @PostMapping("/putProduct")
    public String putProductWeb (Product owerwriteProduct) throws Exception{
        System.out.println("owerwriteProduct 1 " + owerwriteProduct);
        restTemplate = new RestTemplate();
        restTemplate.put(ROOT_URL + "put", owerwriteProduct, Product.class);
        System.out.println("owerwriteProduct 2 " + owerwriteProduct);
        return "redirect:/getController";
    }

    @GetMapping("/delProduct")
    public String delProductWeb (Model model){
        return "DelBase";
    }
    @DeleteMapping("/delProduct")
    public String delProductWeb (@PathVariable (value = "id") Long id){
        restTemplate = new RestTemplate();
        restTemplate.delete(ROOT_URL+"delete", id);
        return "redirect:/getController";
    }

}
