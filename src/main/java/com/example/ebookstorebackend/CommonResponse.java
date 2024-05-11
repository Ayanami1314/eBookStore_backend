package com.example.ebookstorebackend;


public class CommonResponse<T> {

    public String message;
    public T data;
    public boolean ok;

    public CommonResponse(String message, T data, boolean ok) {
        this.message = message;
        this.data = data;
        this.ok = ok;
    }

    public CommonResponse() {
        this.message = null;
        this.data = null;
        this.ok = false;
    }
}
