package com.spavlenko.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ConstraintsViolationException is thrown when some constraints are violated
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated ...")
public class ConstraintsViolationException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;

    public ConstraintsViolationException(String message) {
        super(message);
    }

}
