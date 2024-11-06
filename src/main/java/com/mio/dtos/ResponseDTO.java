package com.mio.dtos;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ResponseDTO(String name,
        String address) {

}
