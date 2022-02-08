package com.store.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long storeId;
    private String productName;
    private BigDecimal cost;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productDate;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getProductDate() {
        return productDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setProductDate(LocalDate productDate) {
        this.productDate = productDate;
    }
    public Product(){};
    public Product(String productName, BigDecimal cost, LocalDate productDate) {
        this.productName = productName;
        this.cost = cost;
        this.productDate = productDate;
    }
    @Override
    public String toString() {
        return "Product{" +
                "storeId=" + storeId +
                ", productName='" + productName + '\'' +
                ", cost=" + cost +
                ", productDate=" + productDate +
                '}';
    }


}
