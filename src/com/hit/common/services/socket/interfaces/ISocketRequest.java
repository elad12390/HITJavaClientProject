package com.hit.common.services.socket.interfaces;


import com.hit.common.services.socket.requests.ParamRequestMap;

public interface ISocketRequest {
    String getMethod();
    String getPath();
    ParamRequestMap getQueryData();
    String getJsonData();
}
