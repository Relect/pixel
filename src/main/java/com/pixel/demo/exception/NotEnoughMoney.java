package com.pixel.demo.exception;

public class NotEnoughMoney extends RuntimeException {

    public NotEnoughMoney(String message) {
        super(message);
    }
}
