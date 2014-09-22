package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Francesco on 22/09/2014.
 */
public class DeleteFolderTask  extends AsyncTask<String, Void, Boolean>

{

    private final String URL = "http://unibox.apphb.com/Services/MainService.svc/DeleteFolder";
    private User user;
    private String path = null;
    private ArrayList<String> dirList = new ArrayList<String>();
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    public DeleteFolderTask(User user) {
        this.user = user;
    }

    private JSONArray jsonList;

    @Override
    protected Boolean doInBackground(String... paths) {
        path = paths[0];
        jsonRequestString = JSONParser.toJSONCreateFolderString(user.getEmail(), user.getAccessToken(), path, "folderName");

        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        try {
            jsonList = new JSONArray(jsonResponseString);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        if(jsonResponseString.contains("true")){
            return true;
        }
        return false;
    }
}
