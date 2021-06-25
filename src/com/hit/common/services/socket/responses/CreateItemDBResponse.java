package com.hit.common.services.socket.responses;

public class CreateItemDBResponse {
    private final Long id;

    public CreateItemDBResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
