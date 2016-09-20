package de.hubrick.challenge.zarutsky.utils;


/**
 * There several ways how to handle with exceptional sutiations during execution either
 * I'm using exceptions mechanism to stop application execution and log exceptional behavior
 */
public class ProcessingException extends RuntimeException {
    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
