package com.example.singaporedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.NamedCacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {
    @Autowired
    private CacheManager cacheManager;

    @Bean("simpleCacheResolver")
    public CacheResolver simpleCacheResolver(){
        SimpleCacheResolver resolver = new SimpleCacheResolver(cacheManager);
        return resolver;
    }

    @Bean("namedCacheResolver")
    public CacheResolver namedCacheResolver(){
        NamedCacheResolver resolver = new NamedCacheResolver(cacheManager,"outLimit");
        return resolver;
    }
}
