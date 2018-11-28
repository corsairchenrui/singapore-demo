package com.example.singaporedemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@Mapper
@CacheConfig(cacheNames = "tx", cacheResolver = "simpleCacheResolver", cacheManager = "caffeine")
public interface PersonMapper {
    @Cacheable(value = "tx", key = "#p0")
    @Select("select * from person where pid=#{id}")
    Person get(String id);
}
