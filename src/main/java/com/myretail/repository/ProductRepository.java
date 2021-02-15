package com.myretail.repository;

import com.myretail.entity.Price;import com.myretail.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
    Product findByPid(Long pid);
}
