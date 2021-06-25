package com.hit.common.services.socket;

import com.hit.common.services.socket.enums.ESocketMethod;
import com.hit.common.services.socket.requests.ParamRequestMap;
import com.hit.common.services.socket.requests.SocketRequest;
import com.hit.common.services.socket.responses.SocketResponse;
import com.hit.driver.MVCDriver;
import com.hit.logger.main.java.Logger;

import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocketService {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public <T extends SocketResponse> Future<T> send(ESocketMethod method, String path, SocketCallback<T> callback) {
        Logger.logDebug("Client sent req {method=" + method.toString() + " , path=" + path + "}");
        Callable<T> task = () -> {
            var request = new SocketRequest(method.toString(), path, null, null);
            SocketExchange exchange = null;

            try {
                exchange = new SocketExchange(new Socket(MVCDriver.serverAddress, MVCDriver.port));
                exchange.send(request);
                var data = exchange.get(SocketResponse.class);
                callback.run((T) data);
                return (T)data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (exchange != null) {
                    exchange.close();
                }
            }
            return null;
        };
        return executor.submit(task);
    }

    public <T extends SocketResponse> Future<T> send(ESocketMethod method, String path, String jsonData, SocketCallback<T> callback) {
        Callable<T> task = () -> {
            var request = new SocketRequest(method.toString(), path, null, jsonData);
            SocketExchange exchange = null;
            try {
                exchange = new SocketExchange(new Socket(MVCDriver.serverAddress, MVCDriver.port));
                exchange.send(request);
                var data = exchange.get(SocketResponse.class);
                callback.run((T) data);
                return (T)data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (exchange != null) {
                    exchange.close();
                }
            }
            return null;
        };
        return executor.submit(task);
    }

    public <T extends SocketResponse> Future<T> send(ESocketMethod method, String path, ParamRequestMap queryData, SocketCallback<T> callback) {
        Callable<T> task = () -> {
            var request = new SocketRequest(method.toString(), path, queryData, null);
            SocketExchange exchange = null;
            try {
                exchange = new SocketExchange(new Socket(MVCDriver.serverAddress, MVCDriver.port));
                exchange.send(request);
                var data = exchange.get(SocketResponse.class);
                callback.run((T) data);
                return (T)data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (exchange != null) {
                    exchange.close();
                }
            }
            return null;
        };
        return executor.submit(task);
    }

    public <T extends SocketResponse> Future<T> send(ESocketMethod method, String path, ParamRequestMap queryData, String jsonData, SocketCallback<T> callback) {
        Callable<T> task = () -> {
            var request = new SocketRequest(method.toString(), path, queryData, jsonData);
            SocketExchange exchange = null;
            try {
                exchange = new SocketExchange(new Socket(MVCDriver.serverAddress, MVCDriver.port));
                exchange.send(request);
                var data = exchange.get(SocketResponse.class);
                callback.run((T) data);
                return (T)data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (exchange != null) {
                    exchange.close();
                }
            }
            return null;
        };
        return executor.submit(task);
    }

    public <T extends SocketResponse, S> Future<T> send(ESocketMethod method, String path, ParamRequestMap queryData, S bodyData, SocketCallback<T> callback) {
        String jsonData = MVCDriver.gson.toJson(bodyData);
        Callable<T> task = () -> {
            var request = new SocketRequest(method.toString(), path, queryData, jsonData);
            SocketExchange exchange = null;
            try {
                exchange = new SocketExchange(new Socket(MVCDriver.serverAddress, MVCDriver.port));
                exchange.send(request);
                var data = exchange.get(SocketResponse.class);
                callback.run((T) data);
                return (T)data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (exchange != null) {
                    exchange.close();
                }
            }
            return null;
        };
        return executor.submit(task);
    }
}
