package com.example.lorenzo.uniboxv20.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class JSONParser {
    public static String getJsonStringFromURL(String url, String jsonRequestString){

        InputStream is = null;

        try {
            // creo la richiesta HTTP POST
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // Inserisco la stringa JSON da inviare
            StringEntity se = new StringEntity(jsonRequestString);
            httpPost.setEntity(se);
            httpPost.setHeader("Content-type", "application/json");

            // Eseguo la richiesta e salvo la response in un InputStream
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parso l'inputStream in un oggetto JSON
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            return sb.toString();

        }catch (Exception e){
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return null;
    }

    public static String toJSONLoginString(String email, String password){
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("password", password);

            return  jsonObj.toString();

        }catch (JSONException e){
            return null;
        }
    }

    public static String toJSONGetDirectoryListString(String email, String accessToken, String path){
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("accessToken", accessToken);
            jsonObj.put("remotePath", path);

            return  jsonObj.toString();

        }catch (JSONException e){
            return null;
        }
    }

    public static String toJSONRegisterNewAccountString(String email, String password, String name, String surname){
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("password", password);
            jsonObj.put("name", name);
            jsonObj.put("surname", surname);
            return jsonObj.toString();
        }catch(JSONException e){
            return null;
        }
    }

    public static String toJSONCreateFolderString(String email, String accessToken, String remotePath, String folderName){
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("accessToken", accessToken);
            jsonObj.put("folderName", folderName);
            jsonObj.put("remotePath", remotePath);
            return jsonObj.toString();
        }catch(JSONException e){
            return null;
        }
    }

    public static String toJSONAddServiceString(String email, String accessToken, String message) {
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("accessToken", accessToken);
            jsonObj.put("message", message);
            return jsonObj.toString();
        }catch(JSONException e){
            return null;
        }
    }
}
