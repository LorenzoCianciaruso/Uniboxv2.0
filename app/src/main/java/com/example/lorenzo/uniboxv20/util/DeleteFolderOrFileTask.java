package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Francesco on 22/09/2014.
 */
public class DeleteFolderOrFileTask extends AsyncTask<String, Void, Boolean>

{
    private final String DELETE_FOLDER_URL = "http://unibox.apphb.com/Services/MainService.svc/DeleteFolder";
    private final String DELETE_FILE_URL = "http://unibox.apphb.com/Services/MainService.svc/DeleteFile";
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String remotePath = strings[2];
        jsonRequestString = JSONParser.toJSONDeleteFileOrDeleteFolderString(email, accessToken, remotePath);

        String URL = "";

        if(remotePath.contains(".")){
            URL = DELETE_FILE_URL;
        }else{
            URL = DELETE_FOLDER_URL;
        }
        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        if(jsonResponseString.contains("true")){
            return true;
        }
        return false;
    }
}
