package com.myretail.gateway.http;

public interface HttpGateway<T>{
    T executeGet(String url, Class<T> c);
}
