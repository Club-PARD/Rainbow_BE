package com.pard.rainbow_be.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
/*
   RuntimeException을 상속받아서 Unchecked Exception으로 활용
   비즈니스 로직에서 실패 처리할 때 CustomException 예외를 발생시켜 응답을 GlobalExceptionHandler에서 공통으로 처리
   throw new CustomException(ErrorCode.INVALID_INPUT_VALUE); 처럼 throw 키워드로 예외를 던져서 GlobalExceptionHandler에서 처리하도록 사용
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}