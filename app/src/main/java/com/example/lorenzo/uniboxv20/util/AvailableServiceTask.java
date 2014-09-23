package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lorenzo on 19/09/2014.
 */
public class AvailableServiceTask extends AsyncTask<String, Void, ArrayList<String>> {

    private final String AVAILABLE_SERVICES = "http://unibox.apphb.com/Services/MainService.svc/GetAvailableServices";
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;
    private ArrayList<String> availableServices = new ArrayList<String>();
    private JSONArray jsonList;

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];

        jsonRequestString = JSONParser.toJSONAvailableServicesString(email, accessToken);
        jsonResponseString = JSONParser.getJsonStringFromURL(AVAILABLE_SERVICES, jsonRequestString);

        try {
            jsonList = new JSONArray(jsonResponseString);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        if (jsonList != null) {
            try {
                for (int i = 0; i < jsonList.length(); i++) {
                    availableServices.add(jsonList.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return availableServices;
    }
}
