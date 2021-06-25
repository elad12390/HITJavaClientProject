package com.hit.common.services.socket;

import com.hit.common.services.socket.responses.SocketResponse;

@FunctionalInterface
public interface SocketCallback<T extends SocketResponse> {
    void run(T data) throws Exception;
}
