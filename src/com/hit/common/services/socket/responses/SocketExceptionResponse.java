package com.hit.common.services.socket.responses;


import com.hit.common.services.socket.enums.ESocketResponseStatus;

public class SocketExceptionResponse<T> extends SocketResponse {
    public SocketExceptionResponse(String message, T data) {
        super(message, ESocketResponseStatus.EXCEPTION, data.toString());
    }

    public SocketExceptionResponse(String message) {
        super(ESocketResponseStatus.EXCEPTION, message);
    }
}
