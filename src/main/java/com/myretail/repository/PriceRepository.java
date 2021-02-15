package com.myretail.repository;

import com.myretail.entity.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends MongoRepository<Price, Long> {
    Price findByProductId(Long pid);
}
