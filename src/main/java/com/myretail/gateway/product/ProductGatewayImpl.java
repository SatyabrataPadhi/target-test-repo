package com.myretail.gateway.product;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.exception.CustomException;
import com.myretail.exception.Error;
import com.myretail.repository.PriceRepository;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.myretail.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ProductGatewayImpl implements ProductGateway{
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoTemplate template;
    @Override
    public Product fetchProductById(Long productId) {
        Product product = getProduct(productId);
        if(product!=null && product.getPid()!=null) {
            Price price = priceRepository.findByProductId(productId);
            product.setPrice(price);
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        Price price = product.getPrice();
        Query query = new Query();
        query.addCriteria(Criteria.where("product_id").is(price.getProductId()));
        Update update = new Update();
        update.set("value", price.getValue());
        price = template.findAndModify(query, update, Price.class);
        product.setPrice(price);
        return product;
    }
    private Product getProduct(Long pid){
        Product product = productRepository.findByPid(pid);
        return product;
    }

    public Product fetchProduct() {
        // TODO:
        //  Need to add the api call with circuit breaker pattern as the retail api does not work
        //  hardcoding to return dummy product info from db
        /*
        try{
         URI uri = URI.create();
         return this.restTemplate.getForObject(uri, Product.class);
         }catch(Exception e){
         throw new CustomException(Error.SERVER_ERROR.getCode());
         }
        */
        return null;
    }

    public void fallback() {
        throw new CustomException(Error.SERVICE_UNAVAILABLE.getCode());
    }

}
