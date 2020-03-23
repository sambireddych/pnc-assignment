package com.useraggregate.useraggregateapplication.jsonResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.JSONPObject;

import com.useraggregate.useraggregateapplication.adapters.Accounts;
import com.useraggregate.useraggregateapplication.adapters.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JsonResponse {


    @JsonSerialize
    public JSONPObject printJsendFormat(Map<User,List<Accounts>> mapAccountsForUser) throws IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        data.put("userDetails", mapAccountsForUser);
        struct.setStatusToSuccess();
        struct.setCode(String.valueOf(HttpStatus.OK));
        struct.setData(data);
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, false);
    }

}
