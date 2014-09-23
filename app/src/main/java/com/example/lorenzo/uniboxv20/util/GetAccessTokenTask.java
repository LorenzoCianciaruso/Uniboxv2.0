package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Lorenzo on 19/09/2014.
 */
public class GetAccessTokenTask extends AsyncTask<String, Void, Boolean> {

    private final String GET_ACC_TOKEN = "http://unibox.apphb.com/Services/MainService.svc/GetAccessTokens";
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;

    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String message = strings[2];
        String authCode = strings[3];
        String user = strings[4];
        String password = strings[5];

        jsonRequestString = JSONParser.toJSONGetAccessTokenString(email, accessToken, message, authCode, user, password);
        jsonResponseString = JSONParser.getJsonStringFromURL(GET_ACC_TOKEN, jsonRequestString);

        if (jsonResponseString.contains("true")) {
            return true;
        }
        return false;
    }
}
