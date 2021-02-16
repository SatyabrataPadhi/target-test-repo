package com.myretail.controller;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
@WebMvcTest(MyRetailController.class)
@EnableMongoRepositories
public class TestController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductUseCase useCase;

    @Test
    public void testGet() throws Exception {
        when(useCase.fetchProductById(1500l)).thenReturn(testProduct());
        this.mockMvc.perform(get("/products/1500")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @Test
    public void testPut() throws Exception {
        when(useCase.updateProduct(testProduct())).thenReturn(testProduct());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/products").content(testProductForPut()).contentType((MediaType.APPLICATION_JSON)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("100.36")));
    }

    private Product testProduct(){
        Product product = new Product();
        product.setName("test");
        product.setPid(1500l);
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue(100.36d);
        price.setProductId(1500l);
        product.setPrice(price);
        return product;
    }

    private String testProductForPut(){
        return "{\n" +
                "\"id\": 1500,\n" +
                "\"name\": \"test\",\n" +
                "\"current_price\": {\n" +
                "\"value\": 100.36,\n" +
                "\"currency_code\": \"USD\"\n" +
                "}\n" +
                "}";
    }
}
