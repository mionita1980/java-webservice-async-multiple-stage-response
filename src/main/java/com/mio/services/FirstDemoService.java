package com.mio.services;

import com.mio.dtos.ResponseDTO;
import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Primary
@Singleton
@CacheConfig("demo")
public class FirstDemoService implements DemoService {

    @Cacheable(parameters = {"input"})
    @Override
    public ResponseDTO getMessage(String input, String secondInput) {
        return new ResponseDTO("testName " + input + " " + secondInput, "testAddress");
    }

}
