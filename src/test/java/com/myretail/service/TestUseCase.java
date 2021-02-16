package com.myretail.service;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.gateway.product.ProductGatewayImpl;
import com.myretail.usecase.product.ProductUseCaseImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class TestUseCase {
    @InjectMocks
    private ProductUseCaseImpl useCase;
    @Mock
    private ProductGatewayImpl gateway;
    @Test
    public void testGetProductUseCase(){
        when(gateway.fetchProductById(1000l)).thenReturn(testProduct());
        Product product = useCase.fetchProductById(1000l);
        Assert.assertNotNull(product);
        Assert.assertEquals("test", product.getName());
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
}
