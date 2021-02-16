package com.myretail.controller;

import com.myretail.usecase.product.ProductUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class MyRetailController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRetailController.class);
    @Autowired
    private ProductUseCase useCase;
    public MyRetailController(ProductUseCase useCase){
        this.useCase = useCase;
    }
    @GetMapping("/products/{pid}")
    public Product getProducts(@PathVariable Long pid){
        LOGGER.info("Inside getProduct");
        com.myretail.entity.Product productEntity = useCase.fetchProductById(pid);
        return generateResponse(productEntity);
    }

    @PutMapping("/products")
    public Product update(@RequestBody @Valid Product product){
        LOGGER.info("Inside update");
        com.myretail.entity.Product productEntity = useCase.updateProduct(generateProduct(product));
        return generateResponse(productEntity);
    }

    //Assume this is an external api
    @GetMapping("/product/{pid}")
    public com.myretail.entity.Product getProduct(@PathVariable Long pid){
        return useCase.getProduct(pid);

    }
    private Product generateResponse(com.myretail.entity.Product product){
        Product response = new Product();
        if(product!=null && product.getPid()!=null) {
            response.setId(product.getPid());
            response.setName(product.getName());

            Price pr = new Price();
            com.myretail.entity.Price price = product.getPrice();
            if(price!=null){
                pr.setCurrency_code(price.getCurrencyCode());
                pr.setValue(price.getValue());
            }
            response.setCurrent_price(pr);
        }

        return response;
    }
    private com.myretail.entity.Product generateProduct(Product response){
        com.myretail.entity.Product product = new com.myretail.entity.Product();
        product.setPid(response.getId());
        product.setName(response.getName());
        com.myretail.entity.Price price = new com.myretail.entity.Price();
        price.setProductId(response.getId());
        price.setValue(response.getCurrent_price().getValue());
        price.setCurrencyCode(response.getCurrent_price().getCurrency_code());
        product.setPrice(price);
        return product;
    }
}
class Product {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private Price current_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Price current_price) {
        this.current_price = current_price;
    }
}
class Price {
    @NotNull
    private double value;
    private String currency_code;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
