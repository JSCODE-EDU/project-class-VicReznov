package toyboard.yoon.exhandler.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toyboard.yoon.exception.RestApiException;
import toyboard.yoon.exhandler.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Developer Custom Exception: 직접 정의한 RestApiException 에러 클래스에 대한 예외 처리
     */
    @ExceptionHandler(RestApiException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(RestApiException ex) {
        GlobalErrorCode globalErrorCode = ex.getGlobalErrorCode();
        return handleExceptionInternal(globalErrorCode);
    }

    // handleExceptionInternal() 메소드를 오버라이딩해 응답 커스터마이징
    private ResponseEntity<ErrorResponse> handleExceptionInternal(GlobalErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode));
    }
}
