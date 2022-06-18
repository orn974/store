package com.store.rest;

import com.store.dao.StoreRepository;
import com.store.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
//@RequestMapping("/")
public class StoreRestController {

    @Autowired
    StoreRepository storeRepository;

    @GetMapping("/products")
    public List methodStart() {
        List list = new ArrayList<>();
        list = storeRepository.findAll();
        return list;
    }
    @GetMapping("/getOne/{id}")
    public Product getOneProduct(@PathVariable Long id){
        Product oneProduct = storeRepository.findById(id).orElse(new Product());
        return oneProduct;
    }

    @GetMapping("/getpost")
    public List methodGetPost() {
        Product productOne = new Product();
        productOne.setCost(BigDecimal.valueOf(33.94));
        productOne.setProductDate(LocalDate.of(2020, 06, 23));
        productOne.setProductName("Book");
        storeRepository.save(productOne);

        List<Product> productList = storeRepository.findAll();
        productList.add(new Product("List", BigDecimal.valueOf(87.21), LocalDate.of(2000, 11, 27)));
        storeRepository.saveAll(productList);

        Product productTwo = new Product("Phone", BigDecimal.valueOf(11.25), LocalDate.of(1977, 3, 20));
        storeRepository.save(productTwo);

        List list = new ArrayList<>();
        list = storeRepository.findAll();
        return list;
    }

    @PostMapping(value = "/post")
    public void post(@RequestBody Product product) {
        System.out.println("Put Product =" + product);
        storeRepository.save(product);
    }
    @PostMapping(value = "/postall")
    public void postAll(@RequestBody ArrayList<Product> product) {
        System.out.println("Put Product =" + product);
        //storeRepository.save(product);
        storeRepository.saveAllAndFlush(product);
    }
    @PutMapping("/put")
    public ResponseEntity<?> put(@RequestBody Product product) {
        if (storeRepository.findById(product.getStoreId()).isPresent()) {
            storeRepository.save(product);
            return new ResponseEntity<>(HttpStatus.CREATED);}
    else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

}
@DeleteMapping("/delete/{storeId}")
    public void deleteProduct (@PathVariable Long storeId){
    storeRepository.deleteById(storeId);
}
}
