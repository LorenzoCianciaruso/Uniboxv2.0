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
public class GetDirectoryListTask extends AsyncTask<String, Void, ArrayList<String>> {

    private final String URL = "http://unibox.apphb.com/Services/MainService.svc/GetDirectoryList";
    private User user;
    private String path = null;
    private ArrayList<String> dirList = new ArrayList<String>();
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private InputStream is = null;
    private JSONArray jsonList;

    public GetDirectoryListTask(User u) {
        this.user = u;
    }

    @Override
    protected ArrayList<String> doInBackground(String... paths) {

        path = paths[0];
        jsonRequestString = JSONParser.toJSONGetDirectoryListString(user.getEmail(), user.getAccessToken(), path);

        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        try {
            jsonList = new JSONArray(jsonResponseString);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        if (jsonList != null) {
            try {
                for (int i = 0; i < jsonList.length(); i++) {
                    dirList.add(jsonList.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return dirList;
    }
}