package com.myretail.usecase.product;

import com.myretail.entity.Product;

public interface ProductUseCase {
    Product fetchProductById(Long productId);
    Product updateProduct(Product product);
    Product getProduct(Long productId);
}
