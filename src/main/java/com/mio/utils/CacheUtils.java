package com.mio.utils;

import com.mio.dtos.ResponseDTO;
import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.CachePut;
import io.micronaut.cache.annotation.Cacheable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CacheConfig("demo")
@Singleton
public class CacheUtils {
    private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);

    @CachePut(parameters = {"name"})
    public ResponseDTO putCacheResponse(String name, ResponseDTO response) {
        log.info("Cached: {} with key: {}", response, name);
        return response;
    }

    @Cacheable(parameters = {"name"})
    public ResponseDTO getCacheResponse(String name, ResponseDTO response) {
        log.info("Cached object not found for key: {}", name);
        return response;
    }

}
