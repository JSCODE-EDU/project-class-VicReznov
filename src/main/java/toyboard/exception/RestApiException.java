package toyboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyboard.yoon.exhandler.advice.GlobalErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final GlobalErrorCode globalErrorCode;
//    private final String message;
}
