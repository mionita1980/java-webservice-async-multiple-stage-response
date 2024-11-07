package com.mio.controllers;

import com.mio.dtos.ResponseDTO;
import com.mio.services.DemoService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/demo")
public class DemoController {

    @Inject
    private DemoService service;

    @Get(uri="/index/{input}/{secondInput}", produces="application/json")
    public ResponseDTO index(String input, String secondInput) {
        return service.getMessage(input, secondInput);
    }

    @Get(uri="/retrieve/{input}", produces="application/json")
    public ResponseDTO retrieve(String input) {
        return service.getCompletedMessage(input);
    }

}
