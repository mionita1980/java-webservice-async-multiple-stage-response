package com.mio.calls;

import com.mio.dtos.ResponseDTO;
import com.mio.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class AddAddressCall implements Callable<ResponseDTO> {
    private static final Logger log = LoggerFactory.getLogger(AddAddressCall.class);

    CacheUtils cacheUtils;
    ResponseDTO initial;
    String input;

    public AddAddressCall(CacheUtils cacheUtils, ResponseDTO initial, String input) {
        this.cacheUtils = cacheUtils;
        this.initial = initial;
        this.input = input;
    }

    @Override
    public ResponseDTO call() throws Exception {
        log.info("Call - Initiating address {} adding to {}", input, initial);
        try {
            log.info("Call - Sleeping...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("Call - Error while sleeping.");
        }
        log.info("Call - Done with sleeping.");
        ResponseDTO response = new ResponseDTO(initial.name(), input);
        log.info("Call - Done with adding address.");
        cacheUtils.putCacheResponse(initial.name(), response);
        log.info("Call - Done with adding address and added it to the cache.");
        //log.info("Test cache retrieval: {}", cacheUtils.getCacheResponse(initial.name(), new ResponseDTO("","")));
        return response;
    }

}
