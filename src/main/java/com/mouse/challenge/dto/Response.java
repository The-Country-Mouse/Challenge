package com.mouse.challenge.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Response<T> {
    private int status;
    private String message;
    private T data;

    private Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(HttpStatus.OK.value(), "Success", data);
    }

    public static <T> Response<T> success() {
        return new Response<>(HttpStatus.OK.value(), "Success", null);
    }

    public static <T> Response<T> error(int status, String message) {
        return new Response<>(status, message, null);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }
}
