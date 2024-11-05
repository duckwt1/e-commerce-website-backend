package com.dacs2_be.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorMessage {
    private final int httpStatus;
    private final String message;
}