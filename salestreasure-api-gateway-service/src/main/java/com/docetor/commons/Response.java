package com.docetor.commons;

import lombok.Data;

@Data
public class Response {
    private String status;
    private String message;
    private Object data;
    private String error;
}
