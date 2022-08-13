package com.store.model;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class ProductTest {

    private Product product = new Product();

    @BeforeAll  // start before all test
    //@Disabled // put off test
    public static void allSetId (){
        System.out.println("BeforeAll");
       // product.setStoreId(44L);
    }

    @BeforeEach // start before each test
    public void setEachStoreId (){
        product.setStoreId(110L);
        System.out.println(product.getStoreId()+"  BeforeEach");
    }


    @Test
    public void getStoreIdTest(){
        product.setStoreId(9L);
        assertTrue(product.getStoreId().equals(9L));
    }

    @Test
    public void limitStoreIdTest (){
        product.setStoreId(-20L);
        assertTrue(product.getStoreId().equals(0L));

    }

    @Test
    public void limitMaxTStoreIdTest(){
        product.setStoreId(101L);
        assertTrue(product.getStoreId().equals(100L));
    }
    @Test
    public void testNoteqalesStoreId (){
        product.setStoreId(60L);
        assertNotEquals(66L, product.getStoreId());
        assertEquals(60L, product.getStoreId());
    }

    @Test
    public void failTest() {
        product.setStoreId(1L);
        System.out.println(product.getStoreId());
        if (product.getStoreId() == 100L) {
            fail("StireId = 100");

        }
    }

}
