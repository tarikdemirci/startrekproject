package io.tarik.startrekproject.klingon.service;

public class UntranslatableCharError extends RuntimeException {
    public UntranslatableCharError(String message) {
        super(message);
    }
}
