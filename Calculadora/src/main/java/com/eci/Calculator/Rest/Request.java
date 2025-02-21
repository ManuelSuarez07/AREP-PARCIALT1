package com.eci.Calculator.Rest;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Manuel S
 */
public class Request {
    private Map<String, String> Params;

    public Request(String queryString) {
        Params = paramsQueryString(queryString);
    }

    public String getValues(String key) {
        return Params.get(key);
    }

    private Map<String, String> paramsQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();
        if (queryString != null && !queryString.isEmpty()) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}


