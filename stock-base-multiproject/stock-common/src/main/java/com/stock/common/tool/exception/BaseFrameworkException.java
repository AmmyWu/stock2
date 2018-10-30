package com.stock.common.tool.exception;


/**
 * 框架的异常的基类
 * 
 */
public class BaseFrameworkException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 5833617058231930745L;

    public BaseFrameworkException() {
        super();
    }

    public BaseFrameworkException(String msg) {
        super(msg);
    }

    public BaseFrameworkException(Throwable t, String msg) {
        super(msg, t);
    }

    public BaseFrameworkException(Throwable t) {
        super(t);
    }
}
