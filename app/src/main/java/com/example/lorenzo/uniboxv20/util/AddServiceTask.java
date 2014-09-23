package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class AddServiceTask extends AsyncTask<String, Void, String> {


    private final String ADD_SERVICE_URL = "http://unibox.apphb.com/Services/MainService.svc/AddService";
    private final String GET_AUTH_URL = "http://unibox.apphb.com/Services/MainService.svc/GetAuthorizationUrl";
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String message = strings[2];

        jsonRequestString = JSONParser.toJSONAddServiceString(email, accessToken, message);
        jsonResponseString = JSONParser.getJsonStringFromURL(ADD_SERVICE_URL, jsonRequestString);

        //TODO da scommentare l'if quando è finito il debug
        //   if(jsonResponseString.contains("true")){
        jsonRequestString = JSONParser.toJSONAddServiceString(email, accessToken, message);
        jsonResponseString = JSONParser.getJsonStringFromURL(GET_AUTH_URL, jsonRequestString);

        if (jsonResponseString.contains("http")) {
            jsonResponseString = jsonResponseString.replace("\"", "").replace("\\", "");
            return jsonResponseString;
        }
        // }
        return null;
    }
}
