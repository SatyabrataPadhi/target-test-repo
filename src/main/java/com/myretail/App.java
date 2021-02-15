package com.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableMongoRepositories
//@EnableCircuitBreaker
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}

