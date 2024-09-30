package com.musinsa.item.exception;

import lombok.Getter;

@Getter
public class ItemCustomException extends RuntimeException {
    private final ErrorEnum errorEnum;

    public ItemCustomException(ErrorEnum errorEnum, String message) {
        super(message);
        this.errorEnum = errorEnum;
    }
}
