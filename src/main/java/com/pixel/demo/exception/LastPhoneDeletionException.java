package com.pixel.demo.exception;

public class LastPhoneDeletionException extends RuntimeException {
    public LastPhoneDeletionException(String phone) {
        super("Cannot delete the last phone of user:" + phone);
    }
}
