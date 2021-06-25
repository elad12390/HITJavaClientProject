package com.hit.common.services.socket.responses;

import com.hit.common.services.socket.enums.ESocketResponseStatus;

public class SocketOKResponse extends SocketResponse {
    public SocketOKResponse(String data) {
        super(ESocketResponseStatus.OK, data);
    }
}
