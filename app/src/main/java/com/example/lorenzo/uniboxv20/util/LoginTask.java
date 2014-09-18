package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class LoginTask extends AsyncTask<String[], Void, User> {
    private final String LOGIN_URL = "http://unibox.apphb.com/Services/MainService.svc/Login";
    private User user = null;
    private InputStream is = null;
    private String jsonRequestString = "";
    private String jsonResponseString = "";
    private JSONObject jObject;

    @Override
    protected User doInBackground(String[]... array) {

        // Costruisco la stringa JSON da inviare
        String[] credentials = array[0];
        jsonRequestString = JSONParser.toJSONLoginString(credentials[0], credentials[1]);

        jsonResponseString = JSONParser.getJsonStringFromURL(LOGIN_URL, jsonRequestString);

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
            if(!mex.equals("Error: Login Failed")) {
                user = new User();
                user.setAccessToken(jObject.getString("accessToken"));
                user.setName(jObject.getString("name"));
                user.setSurname((jObject.getString("surname")));

                user.setEmail(credentials[0]); // l'email la prendo dall'input dell'utente
            }else{
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

}
