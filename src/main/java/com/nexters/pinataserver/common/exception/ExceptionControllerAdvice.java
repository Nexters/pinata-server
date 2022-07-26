package com.nexters.pinataserver.common.exception;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.e5xx.FileUploadException;
import com.nexters.pinataserver.common.exception.e5xx.UnKnownException;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionControllerAdvice {

    private final RedisTemplate<String, String> redisTemplate;

    @ExceptionHandler(ResponseException.class)
    public CommonApiResponse<ResponseException> processException(ResponseException exception) {
        return CommonApiResponse.error(exception);
    }

    @ExceptionHandler(Exception.class)
    public CommonApiResponse<ResponseException> handleException(Exception exception) {
        return CommonApiResponse.error(UnKnownException.UNKNOWN.getResponseException());
    }

}
