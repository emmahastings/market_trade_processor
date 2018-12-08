package com.hastings.market_trade_processor.model;

import org.springframework.http.HttpStatus;

/**
 * Created by emmakhastings on 04/12/2018
 *
 * @author emmakhastings
 * <p>
 * Model class to store potential errors in input that can be returned to client
 */
public final class ExceptionDetails {

    private final HttpStatus httpStatus;

    private final String message;

    public ExceptionDetails(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
