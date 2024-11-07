package com.mio.services;

import com.mio.dtos.ResponseDTO;
import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Singleton
@CacheConfig("demo")
public class FirstDemoService implements DemoService {
    private static final Logger log = LoggerFactory.getLogger(FirstDemoService.class);

    @Override
    public ResponseDTO getMessage(String input, String secondInput) {
        ResponseDTO response = new ResponseDTO(input, null);
        response = addAddress(response, secondInput);
        return response;
    }

    @Cacheable(parameters = {"initial"})
    public ResponseDTO addAddress(ResponseDTO initial, String input) {
        try {
            log.info("Sleeping...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Error while sleeping.");
        }
        return new ResponseDTO(initial.name(), input);
    }
}
