package com.myretail.gateway.product;

import com.myretail.entity.Product;

public interface ProductGateway {
    Product fetchProductById(Long productId);
    Product update(Product product);
}
