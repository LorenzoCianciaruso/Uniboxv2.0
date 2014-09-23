package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class CreateFolderTask extends AsyncTask<String, Void, Boolean> {

    private final String URL = "http://unibox.apphb.com/Services/MainService.svc/CreateFolder";
    private String path = null;
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    @Override
    protected Boolean doInBackground(String... strings) {

        String email = strings[0];
        String accessToken = strings[1];
        String path = strings[2];
        String folderName = strings[3];
        jsonRequestString = JSONParser.toJSONCreateFolderString(email, accessToken, path, folderName);
        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        if (jsonResponseString.contains("true")) {
            return true;
        }

        return false;
    }
}
