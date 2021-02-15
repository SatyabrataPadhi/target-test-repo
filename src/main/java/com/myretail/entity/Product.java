package com.myretail.entity;

/*{"id":13860428,"name":"The Big Lebowski (Blu-ray)
        (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}} */

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "product")
public class Product {
    @Field(name = "product_id")
    private Long pid;

    private String name;
    @DBRef
    private Price price;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
