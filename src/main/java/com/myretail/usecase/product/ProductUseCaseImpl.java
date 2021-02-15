package com.myretail.usecase.product;

import com.myretail.entity.Product;
import com.myretail.gateway.product.ProductGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUseCaseImpl implements ProductUseCase{
    @Autowired
    private ProductGateway productGateway;
    @Override
    public Product fetchProductById(Long productId) {
        return productGateway.fetchProductById(productId);
    }

    @Override
    public Product updateProduct(Product product) {
        return productGateway.update(product);
    }
}
