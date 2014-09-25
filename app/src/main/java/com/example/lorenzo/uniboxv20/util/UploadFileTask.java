package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Francesco on 25/09/2014.
 */
public class UploadFileTask extends AsyncTask<String, Void, Boolean> {

    private final String UPLOAD_FILE = "http://unibox.apphb.com/Services/MainService.svc/UploadFile";
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;
    private JSONArray jsonList;

    @Override
    protected  Boolean doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String data = strings[2];
        String name = strings[3];
        String path = strings[4];

        jsonRequestString = JSONParser.toJSONAUploadFIle(email, accessToken, data, name, path);
        jsonResponseString = JSONParser.getJsonStringFromURL(UPLOAD_FILE, jsonRequestString);

        if (jsonResponseString.contains("true")) {
            return true;
        }
        return false;
    }
}
