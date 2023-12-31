package org.mmx.comment.controller;

import org.mmx.comment.exception.AppException;
import org.mmx.comment.exception.CommentNotFoundErrorMessage;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception handler to process exceptions and errors
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    /**
     * Return 404 Not Found for CommentNotFoundException
     */
    @ExceptionHandler(value = { CommentNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage commentNotFoundException(CommentNotFoundException e, WebRequest request) {
        log.warn("Comment not found", e);
        return new CommentNotFoundErrorMessage(e.getId(), e.getMessage());
    }

    /**
     * Handle Generic AppException
     */
    @ExceptionHandler(value = { AppException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void appException(AppException e, WebRequest request) {
        // may need to handle specific exceptions
        // currently just let Spring handle it
        throw e;
    }
}
