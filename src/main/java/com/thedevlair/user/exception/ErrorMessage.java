package com.thedevlair.user.exception;

import java.util.Date;

public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage(int value, Date date, String message, String description) {
    }
}
