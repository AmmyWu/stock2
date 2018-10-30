package com.stock.common.tool.exception;

/**

/**
 * 
 */
public class WaitTimeoutException extends BaseFrameworkException {
    /**
     * 
     */
    private static final long serialVersionUID = 2571609199859576305L;

    /**
     * Override constructor from Error.
     *
     * @see RuntimeException
     */
    public WaitTimeoutException() {
    }

    /**
     * Override constructor from Error.
     *
     * @param message passed to Error
     * @see RuntimeException
     */
    public WaitTimeoutException(String message) {
        super(message);
    }

    /**
     * Override constructor from Error.
     *
     * @param message passed to Error
     * @param cause   passed to Error
     * @see Error
     */
    public WaitTimeoutException(String message, Throwable cause) {
        super(cause, message);
    }

    /**
     * Override constructor from Error.
     *
     * @param cause passed to Error
     * @see Error
     */
    public WaitTimeoutException(Throwable cause) {
        super(cause);
    }
}
