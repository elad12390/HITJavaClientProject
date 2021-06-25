package com.hit.common.services.socket.enums;

public enum ESocketMethod {
    None, Get, Create, Update, Delete, GetById;
    public static ESocketMethod getFromString(String methodStr) {
        return switch (methodStr) {
            case "GET" -> ESocketMethod.Get;
            case "GET_BY_ID" -> ESocketMethod.GetById;
            case "CREATE" -> ESocketMethod.Create;
            case "UPDATE" -> ESocketMethod.Update;
            case "DELETE" -> ESocketMethod.Delete;
            default -> ESocketMethod.None;
        };
    }
    public String toString() {
        return switch (this) {
            case None -> null;
            case Get -> "GET";
            case GetById -> "GET_BY_ID";
            case Create -> "CREATE";
            case Update -> "UPDATE";
            case Delete -> "DELETE";
        };
    }
}
