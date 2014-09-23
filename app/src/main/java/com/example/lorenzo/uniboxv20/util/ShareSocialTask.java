package com.example.lorenzo.uniboxv20.util;

import android.os.AsyncTask;

import com.example.lorenzo.uniboxv20.data.User;

import org.json.JSONObject;


/**
 * Created by Lorenzo on 22/09/2014.
 */
public class ShareSocialTask extends AsyncTask<String, Void, Boolean> {
    private static final String SHARE_FACEBOOK_URL = "http://unibox.apphb.com/Services/MainService.svc/ShareOnFacebook";
    private static final String SHARE_TWITTER_URL = "http://unibox.apphb.com/Services/MainService.svc/ShareOnTwitter";
    private static final String SHARE_YOUTUBE_URL = "http://unibox.apphb.com/Services/MainService.svc/UploadVideo";
    private String jsonRequestString = "";
    private String jsonResponseString = "";

    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String accessToken = strings[1];
        String remotePath = strings[2];
        String socialSelected = strings[3];

        jsonRequestString = JSONParser.toJSONShareBySocialString(email, accessToken, remotePath);
        String URL = "";
        if (socialSelected.equals("facebook")) {
            URL = SHARE_FACEBOOK_URL;
        } else if (socialSelected.equals("twitter")) {
            URL = SHARE_TWITTER_URL;
        } else if (socialSelected.equals("youtube")) {
            URL = SHARE_YOUTUBE_URL;
        }

        jsonResponseString = JSONParser.getJsonStringFromURL(URL, jsonRequestString);

        if (jsonResponseString.contains("true")) {
            return true;
        }
        return false;
    }
}
