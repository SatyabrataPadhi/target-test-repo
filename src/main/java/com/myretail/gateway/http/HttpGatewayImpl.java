package com.myretail.gateway.http;

import com.myretail.entity.Product;
import com.myretail.exception.CustomException;
import com.myretail.exception.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpGatewayImpl<T> implements HttpGateway<T>{
    @Override
    public T executeGet(String url, Class<T> c) {
        try {
            RestTemplate template = new RestTemplate();
            return template.getForObject(url, c);
        }catch(Exception e){
            throw new CustomException(Error.SERVER_ERROR.getCode());
        }

    }
}
