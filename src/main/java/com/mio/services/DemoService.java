package com.mio.services;

import com.mio.dtos.ResponseDTO;

public interface DemoService {
    ResponseDTO getMessage(String input, String secondInput);
    ResponseDTO getCompletedMessage(String input);
}
