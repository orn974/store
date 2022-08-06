package com.store.model;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;


public class ProductTest {

    private Product product = new Product();
    @BeforeAll  // start before all test
    @Disabled // put off test
    public void allSetId (){
        product.setStoreId(44L);
    }

    @BeforeEach // start before each test
    public void setEachStoreId (){
        product.setStoreId(110L);
        System.out.println(product.getStoreId()+"DDDDDDDDDDD");
    }


    @Test
    public void getStoreIdTest(){
        product.setStoreId(9L);
       //assertTrue(product.getStoreId().equals(9L));
        Assertions.assertTrue(product.getStoreId().equals(9L));
    }

    @Test
    public void limitStoreIdTest (){
        product.setStoreId(-20L);
        Assertions.assertTrue(product.getStoreId().equals(0L));

    }

    @Test
    public void limitMaxTStoreIdTest(){
        product.setStoreId(101L);
        Assertions.assertTrue(product.getStoreId().equals(100L));
    }
    @Test
    public void testNoteqalesStoreId (){
        product.setStoreId(60L);
        Assertions.assertNotEquals(java.util.Optional.of(66L), product.getStoreId());
        Assertions.assertEquals(Long.valueOf(60), product.getStoreId());
    }

    @Test
    public void failTest() {
        product.setStoreId(100l);
        System.out.println(product.getStoreId());
        if (product.getStoreId() == 100L) {
            Assertions.fail("StireId = 100");

        }
    }

}
