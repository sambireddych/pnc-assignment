package com.accounts.accountapplication.jsonResponse;

import java.util.HashMap;
import java.util.Map;

public class JsonData {

    private Map<String, Object> keysValuesMap = new HashMap<String, Object>();

    public Map<String, Object> getKeysValuesMap() {
        return keysValuesMap;
    }

    public void put(String key, Object value) {
        keysValuesMap.put(key, value);
    }
}
