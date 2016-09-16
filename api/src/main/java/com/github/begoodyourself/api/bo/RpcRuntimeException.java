package com.github.begoodyourself.api.bo;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class RpcRuntimeException extends RuntimeException{
    private int errorcode;
    private String errorMsg;

    public RpcRuntimeException(int errorcode, String errorMsg) {
        this.errorcode = errorcode;
        this.errorMsg = errorMsg;
    }
    public RpcRuntimeException(String message, Throwable cause, int errorcode) {
        super(message, cause);
        this.errorcode = errorcode;
        this.errorMsg = message;
    }

    public RpcRuntimeException(Throwable cause, int errorcode) {
        super(cause);
        this.errorcode = errorcode;
        this.errorMsg = cause.getMessage();
    }

    public RpcRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorcode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorcode = errorcode;
        this.errorMsg = message;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
