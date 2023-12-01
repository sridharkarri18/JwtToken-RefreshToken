package Jwt.Security.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.webjars.NotFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurity(Exception ex) {
        ProblemDetail error1 = null;
        if (ex instanceof BadCredentialsException) {
            error1 = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
            error1.setProperty("access denied ", "Username is invalid");}
        if (ex instanceof AccessDeniedException) {
            error1 = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            error1.setProperty("access denied", "not have Access for you ");}
        if (ex instanceof SignatureException) {
            error1 = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            error1.setProperty("access denied","Invalid Signature");}
        if (ex instanceof ExpiredJwtException) {
            error1 = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            error1.setProperty("access denied", "Token Expired");}

        return error1;
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail refresh(RuntimeException e){
        ProblemDetail problemDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
        return problemDetail;
    }

}
