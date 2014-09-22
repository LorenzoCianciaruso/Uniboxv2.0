package com.example.lorenzo.uniboxv20.util;


import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Francesco on 22/09/2014.
 */
public class SpaceLeftTask extends AsyncTask<String, Void, ArrayList<String>> {

    private final String AVAILABLE_SERVICES = "http://unibox.apphb.com/Services/MainService.svc/GetSpaceInfo";
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;
    private ArrayList<String> spaceLeft = new ArrayList<String>();
    private JSONArray jsonList;

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];

        jsonRequestString = JSONParser.toJSONSpaceInfoOrBrokenConnectionsString(email, accessToken);
        jsonResponseString = JSONParser.getJsonStringFromURL(AVAILABLE_SERVICES, jsonRequestString);

       // if (josn)
        //TODO restituire struttura dati
        return spaceLeft;
    }
}
