package com.nexters.pinataserver.common.exception;

import com.nexters.pinataserver.common.exception.e5xx.FileUploadException;
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
    public ResponseEntity<ResponseException> processException(HttpServletRequest request, ResponseException response) {
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ResponseException> fileUploadException(Exception exception) {
        ResponseException responseException = new ResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );
        return new ResponseEntity<>(responseException, HttpStatus.valueOf(responseException.getStatus()));
    }

}
