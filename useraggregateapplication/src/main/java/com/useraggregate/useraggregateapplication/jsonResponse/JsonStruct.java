package com.useraggregate.useraggregateapplication.jsonResponse;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = JsonStructSerializer.class)
public class JsonStruct {
    public final static String STATUS = "status";
    public final static String DATA = "data";
    public final static String ERROR = "errors";
    public final static String MESSAGE = "message";
    public final static String CODE = "code";

    private JsonStatusEnum status = null;
    private String message = null;
    private String code = null;
    private JsonData data;
    private JsonData errors;


    public JsonData getErrors() {
        return errors;
    }

    public void setErrors(JsonData errors) {
        this.errors = errors;
    }

    public JsonData getData() {
        return data;
    }

    public void setData(JsonData data) {
        this.data = data;
    }

    public void setStatusToSuccess() {
        this.setStatus(JsonStatusEnum.SUCCESS);
    }


    public void setStatusToError() {
        this.setStatus(JsonStatusEnum.ERROR);
    }

    public JsonStatusEnum getStatus() {
        return this.status;
    }

    private void setStatus(JsonStatusEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.status == JsonStatusEnum.SUCCESS;
    }


    public boolean isError() {
        return this.status == JsonStatusEnum.ERROR;
    }

}
