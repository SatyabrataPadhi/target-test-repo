package com.myretail.gateway.product;

import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.exception.CustomException;
import com.myretail.exception.Error;
import com.myretail.gateway.http.HttpGateway;
import com.myretail.repository.PriceRepository;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.myretail.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductGatewayImpl implements ProductGateway{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGatewayImpl.class);
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private HttpGateway<Product> httpGateway;
    @Value("${product.service.url}")
    private String serviceUrl;
    @Override
    public Product fetchProductById(Long productId) {
        Product product = httpGateway.executeGet(serviceUrl+productId, Product.class);
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
    public Product getProduct(Long pid){
        return productRepository.findByPid(pid);
    }
}
