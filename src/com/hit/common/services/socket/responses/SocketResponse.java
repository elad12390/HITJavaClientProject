package com.hit.common.services.socket.responses;

import com.hit.common.services.socket.enums.ESocketResponseStatus;
import com.hit.common.services.socket.interfaces.ISocketResponse;
import com.hit.driver.MVCDriver;

public class SocketResponse implements ISocketResponse<String> {
    private final ESocketResponseStatus statusCode;
    private final String data;

    public SocketResponse(String message, ESocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public SocketResponse(ESocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public ESocketResponseStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDataJson() {
        return data;
    }

    public <T> T getData(Class<T> c) {
        return MVCDriver.gson.fromJson(this.getDataJson(), c);
    }

    public Boolean isSuccessful() {
        return this.statusCode == ESocketResponseStatus.OK;
    }

    @Override
    public String toString() {
        return "SocketResponse{" +
                "statusCode=" + statusCode +
                ", data='" + data + '\'' +
                '}';
    }
}
