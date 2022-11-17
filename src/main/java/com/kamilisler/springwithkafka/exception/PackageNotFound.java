package com.kamilisler.springwithkafka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PackageNotFound extends RuntimeException {

    public PackageNotFound(String message) {
        super(message);
    }


}
