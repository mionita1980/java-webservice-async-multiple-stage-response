package com.mio.services;

import com.mio.calls.AddAddressCall;
import com.mio.dtos.ResponseDTO;
import com.mio.utils.CacheUtils;
import io.micronaut.context.annotation.Primary;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

@Primary
@Singleton
public class FirstDemoService implements DemoService {
    private static final Logger log = LoggerFactory.getLogger(FirstDemoService.class);

    @Inject
    CacheUtils cacheUtils;

    @Override
    public ResponseDTO getMessage(String input, String secondInput) {
        log.info("Calling getMessage with {} and {}", input, secondInput);
        ResponseDTO response = new ResponseDTO(input, null);
        this.getMessageContinue(response, secondInput);
        log.info("Returning from getMessage...");
        return response;
    }

    @Async
    public void getMessageContinue(ResponseDTO response, String secondInput) {
        log.info("Calling getMessageContinue with {}", secondInput);
        Future<ResponseDTO> future;
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            AddAddressCall call = new AddAddressCall(cacheUtils, response, secondInput);
            log.info("Submitting call...");
            future = executor.submit(call);
        }
        log.info("Returning from getMessageContinue...");
    }


    @Override
    public ResponseDTO getCompletedMessage(String input) {
        log.info("Calling getCompletedMessage with {}", input);
        ResponseDTO initialOne = new ResponseDTO(input, null);
        ResponseDTO finalOne = cacheUtils.getCacheResponse(input, initialOne);
        if (finalOne == null) {
            log.warn("Final response is not ready");
            return initialOne;
        } else {
            log.warn("Final response is ready: {}", finalOne);
            return finalOne;

        }
    }

}
