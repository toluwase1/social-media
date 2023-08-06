package com.example.socialmediaapi.response;

import lombok.Data;

@Data
public class CustomResponse<T> {
    private boolean success;
    private boolean error;
    private Object responseData;
    private int statusCode;

    public CustomResponse(boolean success, boolean error, Object responseData, int statusCode) {
        this.success = success;
        this.error = error;
        this.responseData = responseData;
        this.statusCode = statusCode;
    }
    public boolean isError() {
        return error;
    }
}
