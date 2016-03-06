package org.marek.retrofit2usage.rest.response;

/**
 * Created by Marek on 2014-04-23.
 */
public class TransportModel<T> {

    private String message;
    private String errorCode;
    private boolean success;
    private T content;

    public TransportModel() {
        super();
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(final T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TransportModel{" +
                "message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", success=" + success +
                ", content=" + content +
                '}';
    }
}
