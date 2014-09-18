package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class RegisterNewAccountTask extends AsyncTask<String[], Void, User> {

    private static final String URL = "http://unibox.apphb.com/Services/MainService.svc/RegisterNewAccount";
    private User user;
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private InputStream is = null;
    private JSONObject jObject;

    @Override
    protected User doInBackground(String[]... strings) {
        // Costruisco la stringa JSON da inviare
        String[] userDetails = strings[0];
        jsonRequestString = JSONParser.toJSONRegisterNewAccountString(userDetails[0], userDetails[1], userDetails[2], userDetails[3]);

        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        try {
            jObject = new JSONObject(jsonResponseString);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // Creo un oggetto User
        try{
            // prendo la stringa nell'array message
            JSONArray messages = jObject.getJSONArray("message");
            String mex = messages.get(0).toString();

            // Se il messaggio non è di errore creo l'utente
            if(!mex.equals("Email is already used, choose another one")) {
                user = new User();
                user.setAccessToken(jObject.getString("accessToken"));
                user.setName(jObject.getString("name"));
                user.setSurname((jObject.getString("surname")));
                user.setEmail((jObject.getString("email")));
            }else{
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
