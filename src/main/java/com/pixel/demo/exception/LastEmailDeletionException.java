package com.pixel.demo.exception;

public class LastEmailDeletionException extends RuntimeException {
    public LastEmailDeletionException(String email) {
        super("Cannot delete the last email of user:" + email);
    }
}