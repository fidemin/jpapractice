package org.yunhongmin.shop.exception;

public class NotEnoughStockException extends IllegalArgumentException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
