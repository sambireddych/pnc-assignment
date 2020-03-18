package com.user.userapplication.handlers;

import com.user.userapplication.jsonResponse.JsonData;
import com.user.userapplication.jsonResponse.JsonStruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private JsonStruct parseError(Exception ex, WebRequest request) {
        JsonStruct jsonStruct = new JsonStruct();
        jsonStruct.setData(null);
        jsonStruct.setStatusToError();
        return jsonStruct;
    }

    private ApiError error(Exception ex, WebRequest request) {
        ApiError error = new ApiError();
        error.setDetail(ex.getMessage());
        error.setSource(request.getDescription(false).split("=")[1]);
        return error;
    }


    @ExceptionHandler(NoSuchMethodException.class)
    protected ResponseEntity<Object> handleNotFoundException(NoSuchMethodException ex, WebRequest request){
        JsonStruct error = parseError(ex, request);
        ApiError apiError = error(ex,request);
        apiError.setTitle("No Element is present in database");
        JsonData jsonData = new JsonData();
        jsonData.put("error",apiError);
        error.setData(null);
        error.setErrors(jsonData);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<JsonStruct> handleUnprocessableEntity(IllegalArgumentException ex, WebRequest request) {
        JsonStruct error = parseError(ex, request);
        error.setCode(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY));
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        JsonStruct error = parseError(ex, request);
        error.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        error.setMessage("The request is invalid. Please refer to the documentation");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<JsonStruct> handleGlobalException(Exception ex, WebRequest request) {
        JsonStruct error = parseError(ex, request);
        ApiError apiError = error(ex,request);
        apiError.setTitle("Server error");
        JsonData jsonData = new JsonData();
        jsonData.put("error",apiError);
        error.setData(null);
        error.setErrors(jsonData);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
