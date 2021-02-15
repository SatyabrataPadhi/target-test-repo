package com.myretail.controller;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.usecase.product.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRetailController {
    @Autowired
    private ProductUseCase useCase;
    @GetMapping("/products/{pid}")
    public ProductResponse getProducts(@PathVariable Long pid){
        Product productEntity = useCase.fetchProductById(pid);
        return generateResponse(productEntity);
    }
    @PutMapping("/products/{pid}")
    public ProductResponse update(@PathVariable Long pid, @RequestBody ProductResponse product){
        Product productEntity = useCase.updateProduct(generateProduct(product));
        return generateResponse(productEntity);
    }

    private ProductResponse generateResponse(Product product){
        ProductResponse response = new ProductResponse();
        if(product!=null && product.getPid()!=null) {
            response.id = product.getPid();
            response.name = product.getName();

            PriceResponse pr = new PriceResponse();
            Price price = product.getPrice();
            if(price!=null){
                pr.currency_code = price.getCurrencyCode();
                pr.value = price.getValue();
            }
            response.current_price = pr;
        }

        return response;
    }
    private Product generateProduct(ProductResponse response){
        Product product = new Product();
       product.setPid(response.id);
       product.setName(response.name);
        Price price = new Price();
        price.setProductId(response.id);
        price.setValue(response.current_price.value);
        price.setCurrencyCode(response.current_price.currency_code);
        product.setPrice(price);
        return product;
    }
}
class ProductResponse{
    public Long id;
    public String name;
    public PriceResponse current_price;
}
class PriceResponse{
    public double value;
    public String currency_code;
}
