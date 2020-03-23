package com.accounts.accountapplication.jsonResponse;

import com.accounts.accountapplication.domain.Accounts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
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
    public JSONPObject printAccount(Accounts accounts) throws IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        Map<String, Object> mapAccount = new HashMap<String, Object>();
        mapAccount.put("AccountId", String.valueOf(accounts.getId()));
        mapAccount.put("AccountName", accounts.getAccountName());
        mapAccount.put("AccountNumber", accounts.getAccountNumber());
        mapAccount.put("BankName", accounts.getBankName());
        mapAccount.put("AccountType",accounts.getAccountType());

        data.put("AccountInfo", mapAccount);
        struct.setStatusToSuccess();
        struct.setCode(String.valueOf(HttpStatus.OK));
        struct.setData(data);
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, null);
    }

    public JSONPObject printArrayAccounts(List<Accounts> accounts) throws  IOException {
        JsonStruct struct = new JsonStruct();
        JsonData data = new JsonData();
        Map<String, Object> mapAccount = new HashMap<String, Object>();
        List<Map<String, Object>> listOfUsers = new ArrayList<Map<String, Object>>();
        for (Accounts account : accounts) {
            mapAccount.put("AccountId", String.valueOf(account.getId()));
            mapAccount.put("AccountName", account.getAccountName());
            mapAccount.put("AccountNumber", String.valueOf(account.getAccountNumber()));
            mapAccount.put("BankName", account.getBankName());
            mapAccount.put("AccountType", String.valueOf(account.getAccountType()));
            listOfUsers.add(new HashMap<>(mapAccount));
        }

        data.put("AccountsList", listOfUsers);
        struct.setStatusToSuccess();
        struct.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, struct);
        String s = out.toString();
        return new JSONPObject(s, null);
    }
}
