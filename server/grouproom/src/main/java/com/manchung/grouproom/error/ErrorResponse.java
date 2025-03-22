package com.manchung.grouproom.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private int status;
    private String message;
}
