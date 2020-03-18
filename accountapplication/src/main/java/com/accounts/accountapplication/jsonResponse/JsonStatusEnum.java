package com.accounts.accountapplication.jsonResponse;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonStatusEnum {
    SUCCESS("success"), ERROR("error");
    private final String status;

    private JsonStatusEnum(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.status.toLowerCase();
    }
}
