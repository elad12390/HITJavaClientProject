package com.hit.common.services.socket.interfaces;

import com.hit.common.services.socket.enums.ESocketResponseStatus;

public interface ISocketResponse<T> {
    ESocketResponseStatus getStatusCode();
    T getDataJson();
}
