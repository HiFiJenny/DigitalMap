package com.comac.common.core.exception;

/**
 * 验证码错误异常类
 * 
 * @author ruoyi
 */
public class CustomException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public CustomException(String msg)
    {
        super(msg);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
