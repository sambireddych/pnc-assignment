package com.user.userapplication.jsonResponse;

public class JsonStatus {
    private JsonStatusEnum status;

    public JsonStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JsonStatusEnum status) {
        this.status = status;
    }

    public void setStatusToSuccess() {
        this.status = JsonStatusEnum.SUCCESS;
    }


    public void setStatusToError() {
        this.status = JsonStatusEnum.ERROR;
    }
}
