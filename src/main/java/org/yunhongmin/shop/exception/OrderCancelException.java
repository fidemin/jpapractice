package org.yunhongmin.shop.exception;

public class OrderCancelException extends RuntimeException {
    public OrderCancelException(String msg) {
        super(msg);
    }
}
