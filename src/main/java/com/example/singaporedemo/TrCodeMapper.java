package com.example.singaporedemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@Mapper
@CacheConfig(cacheNames = "tx", cacheResolver = "simpleCacheResolver", cacheManager = "caffeine")
public interface TrCodeMapper {
    @Cacheable(value = "tx", key = "#p0")
    @Select("select * from tx where trcode=#{id}")
    BoeingTx getTx(String id);
}
