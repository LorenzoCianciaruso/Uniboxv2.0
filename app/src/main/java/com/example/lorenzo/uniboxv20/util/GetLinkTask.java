package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;

/**
 * Created by Lorenzo on 24/09/2014.
 */
public class GetLinkTask extends AsyncTask<String, Void, String> {

    private final String GET_LINK_URL = "http://unibox.apphb.com/Services/MainService.svc/GetDownloadUrl";
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String remotePath = strings[2];

        jsonRequestString = JSONParser.toJSONDownloadFileOrLinkString(email, accessToken, remotePath);
        jsonResponseString = JSONParser.getJsonStringFromURL(GET_LINK_URL, jsonRequestString);

        jsonResponseString = jsonResponseString.replace("\"", "").replace("\\", "");

        return jsonResponseString;
    }
}
