package org.mmx.comment.controller;

import org.mmx.comment.exception.CommentNotFoundErrorMessage;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handler to process exceptions and errors
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    /**
     * Return 404 Not Found for CommentNotFoundException
     */
    @ExceptionHandler(value = { CommentNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage commentNotFoundException(CommentNotFoundException e, WebRequest request) {
        return new CommentNotFoundErrorMessage(e.getId(), e.getMessage());
    }

}
