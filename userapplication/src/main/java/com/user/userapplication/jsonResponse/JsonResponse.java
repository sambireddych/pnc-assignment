package com.user.userapplication.jsonResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.user.userapplication.adapters.Accounts;
import com.user.userapplication.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonDeserialize
@Component
public class JsonResponse {


    @JsonSerialize
    public JSONPObject printUserInfo(User user) throws IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        Map<String, Object> mapUser = new HashMap<String, Object>();
        mapUser.put("userId", String.valueOf(user.getId()));
        mapUser.put("firstName", user.getFirstName());
        mapUser.put("lastName", user.getLastName());
        mapUser.put("phoneNumber", user.getPhoneNo());
//        mapUser.put("SSN", user.getSsn());

        data.put("userInfo", mapUser);
        struct.setStatusToSuccess();
        struct.setCode(String.valueOf(HttpStatus.OK));
        struct.setData(data);
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, null);
    }

    public JSONPObject printUsersList(List<User> users) throws  IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        Map<String, String> mapUser = new HashMap<String, String>();
        List<Map<String, String>> listOfUsers = new ArrayList<Map<String, String>>();
        for (User user : users) {
            mapUser.put("userId", String.valueOf(user.getId()));
            mapUser.put("firstName", user.getFirstName());
            mapUser.put("lastName", user.getLastName());
            mapUser.put("phoneNumber", user.getPhoneNo());
//            mapUser.put("SSN", user.getSsn());
            listOfUsers.add(new HashMap<>(mapUser));
        }

        data.put("UsersList", listOfUsers);
        struct.setStatusToSuccess();
        struct.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, null);
    }

    public JSONPObject printJsendWithAccountData(List<Accounts> accounts,User user) throws IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        Map<String, String> mapAccount = new HashMap<String, String>();
        List<Map<String, String>> listOfAccounts = new ArrayList<Map<String, String>>();

        mapAccount.put("firstName",user.getFirstName());
        mapAccount.put("lastName",user.getLastName());
        for (Accounts account : accounts) {
            mapAccount.put("accountName", account.getAccountName());
            mapAccount.put("bankName", account.getBankName());
            mapAccount.put("accountNumber", String.valueOf(account.getAccountNumber()));
            mapAccount.put("accountType",account.getAccountType());
            listOfAccounts.add(new HashMap<>(mapAccount));
        }

        data.put("Accounts", listOfAccounts);
        struct.setStatusToSuccess();
        struct.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, null);

    }
}
