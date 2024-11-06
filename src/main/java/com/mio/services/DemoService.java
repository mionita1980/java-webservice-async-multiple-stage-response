package com.mio.services;

import com.mio.dtos.ResponseDTO;

public interface DemoService {
    ResponseDTO getMessage(String input, String secondInput);
}
