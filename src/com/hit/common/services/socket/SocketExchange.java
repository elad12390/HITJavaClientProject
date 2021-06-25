package com.hit.common.services.socket;


import com.hit.common.services.socket.interfaces.ISocketExchange;
import com.hit.common.services.socket.requests.SocketRequest;
import com.hit.driver.MVCDriver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketExchange implements ISocketExchange {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String request;

    public SocketExchange(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    @Override
    public SocketRequest getRequest() {
        if (this.request == null) {
            try {
                this.request = in.readUTF();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return this.request != null ? MVCDriver.gson.fromJson(this.request, SocketRequest.class) : null;
    }

    @Override
    public <T> T get(Class<T> c) {
        if (this.request == null) {
            try {
                this.request = in.readUTF();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return this.request != null ? MVCDriver.gson.fromJson(this.request, c) : null;
    }

    @Override
    public <T> void send(T data) {
        String output = MVCDriver.gson.toJson(data);
        try {
            out.writeUTF(output);
            out.flush();
        } catch (Exception e) {
            System.out.println("Socket error " + e.toString());
        }

    }

    @Override
    public void close() {
        try {
            if (!this.socket.isClosed())
                this.socket.close();
        } catch (Exception e) {
            System.out.println("Could not close socket");
        }
    }


}
