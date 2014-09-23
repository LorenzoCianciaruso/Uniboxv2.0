package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;

/**
 * Created by Lorenzo on 22/09/2014.
 */
public class ShareByEmailTask extends AsyncTask<String[], Void, Boolean> {
    private static final String SHARE_BY_EMAIL_URL = "http://unibox.apphb.com/Services/MainService.svc/ShareWithMail";
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    @Override
    protected Boolean doInBackground(String[]... strings) {
        String[] credentials = strings[0];
        String[] addresses = strings[1];
        String email = credentials[0];
        String accessToken = credentials[1];
        String remotePath = credentials[2];
        jsonRequestString = JSONParser.toJSONShareByEmailString(email, accessToken, remotePath, addresses);
        jsonResponseString = JSONParser.getJsonStringFromURL(SHARE_BY_EMAIL_URL, jsonRequestString);

        if (jsonResponseString.contains("true")) {
            return true;
        }
        return false;
    }
}
