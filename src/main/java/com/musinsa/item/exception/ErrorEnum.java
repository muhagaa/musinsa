package com.musinsa.item.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    NOT_VALID_CATEGORY("0001"),
    NO_CONTENT_CATEGORY("0002"),
    ALREADY_EXIST_ITEM("0003"),
    NO_EXIST_ITEM("0004"),
    METHOD_ARGUMENT_NOT_VALID("0005"),
    UNHANDLED_EXCEPTION("9999")
    ;

    private final String code;

    ErrorEnum(String code) {
        this.code = code;
    }
}
