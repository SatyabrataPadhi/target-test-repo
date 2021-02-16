package com.myretail.service;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.gateway.http.HttpGatewayImpl;
import com.myretail.gateway.product.ProductGatewayImpl;
import com.myretail.repository.PriceRepository;
import com.myretail.repository.ProductRepository;
import com.myretail.usecase.product.ProductUseCaseImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
@RunWith(MockitoJUnitRunner.class)
public class TestGateway {
    @InjectMocks
    private ProductGatewayImpl gateway;
    @Mock
    private ProductRepository productRepo;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private HttpGatewayImpl<Product> httpGateway;

    @Test
    public void testGateway(){
        //when(productRepo.findByPid(1000l)).thenReturn(testProduct());
       when(priceRepository.findByProductId(1000l)).thenReturn(testPrice());
       when(httpGateway.executeGet(any(String.class), any(Class.class))).thenReturn(testProduct());
       Product product =  gateway.fetchProductById(1000l);
        Assert.assertNotNull(product);
        Assert.assertEquals("test", product.getName());


    }

    private Price testPrice(){
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue(100.36d);
        price.setProductId(1500l);
        return price;
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
