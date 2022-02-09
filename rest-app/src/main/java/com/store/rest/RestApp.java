package com.store.rest;

import com.store.dao.StoreRepository;
import com.store.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@EnableJpaRepositories("com.store.dao")
@EntityScan("com.store.model")
public class RestApp {

        public static void main( String[] args )
        {
            SpringApplication.run(RestApp.class, args);

        }
        @Bean
        public CommandLineRunner autoPost (StoreRepository storeRepository){
            return (args) -> {
                storeRepository.save(new Product("Phones", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones2", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones3", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones4", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));storeRepository.save(new Product("Phones", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones2", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones3", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones4", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));storeRepository.save(new Product("Phones", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones2", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones3", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones4", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));storeRepository.save(new Product("Phones", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones2", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones3", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
                storeRepository.save(new Product("Phones4", BigDecimal.valueOf(12.25), LocalDate.of(1987,6,22)));
            };
        }
}
