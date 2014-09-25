package com.example.lorenzo.uniboxv20;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.GetAccessTokenTask;


public class WebViewActivity extends Activity {

    private String service;
    private User currentUser;
    private boolean control = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final WebView myWebView = (WebView) findViewById(R.id.webView);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);


        String url = getIntent().getStringExtra("url");
        service = getIntent().getStringExtra("service");
        currentUser = (User) getIntent().getExtras().getSerializable("user");

        myWebView.setWebViewClient(new WebViewClient() {

                                       @Override
                                       public void onLoadResource(WebView view, String url) {

                                           String originalUrl = myWebView.getUrl();

                                           if (service.equals("dropbox")) {
                                               if (originalUrl.contains("authorize_submit")) {
                                                   GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {
                                                       @Override
                                                       protected void onPostExecute(Boolean response) {
                                                           if (response) {
                                                               Toast.makeText(WebViewActivity.this, "Access token true", Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               Toast.makeText(WebViewActivity.this, "Access token false", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   };
                                                   getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), service, "", "", "");
                                                  // Intent intent = new Intent(WebViewActivity.this, NavigatorActivity.class);
                                                  // intent.putExtra("user", currentUser);
                                                  // startActivity(intent);
                                                   finish();
                                               }
                                           } else if (service.equals("box")) {
                                               if (originalUrl.contains("https://www.google.it/?state=security_token") && originalUrl.contains("code")) {
                                                   String code;
                                                   String[] splittedString = originalUrl.split("=");

                                                   code = splittedString[splittedString.length - 1];

                                                   GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {
                                                       @Override
                                                       protected void onPostExecute(Boolean response) {
                                                           if (response) {
                                                               Toast.makeText(WebViewActivity.this, "Access token true", Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               Toast.makeText(WebViewActivity.this, "Access token false", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   };
                                                   getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), service, code, "", "");
                                                   //Intent intent = new Intent(WebViewActivity.this, NavigatorActivity.class);
                                                  // intent.putExtra("user", currentUser);
                                                  // startActivity(intent);
                                                   finish();
                                               }

                                           } else if (service.equals("facebook")) {
                                               //problema qui da risolvere
                                               if (originalUrl.contains("http://www.example.com/?code=")) {
                                                   String code;
                                                   String[] splittedString = originalUrl.split("code");

                                                   code = splittedString[1];
                                                   code = code.replaceFirst("=", "");

                                                   GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {
                                                       @Override
                                                       protected void onPostExecute(Boolean response) {
                                                           if (response) {
                                                               Toast.makeText(WebViewActivity.this, "Access token true", Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               Toast.makeText(WebViewActivity.this, "Access token false", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   };
                                                   getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), service, code, "", "");
                                                   //Intent intent = new Intent(WebViewActivity.this, NavigatorActivity.class);
                                                  // intent.putExtra("user", currentUser);
                                                   //startActivity(intent);
                                                   finish();
                                               }

                                           } else if (service.equals("twitter")) {

                                           } else if (service.equals("youtube")) {
                                               if (originalUrl.contains("www.example.com/oauth2callback?state=stringa&code=")) {
                                                   String code;
                                                   String[] splittedString = originalUrl.split("&");
                                                   String codeField = splittedString[splittedString.length - 1];
                                                   String[] codeFieldSplit = codeField.split("=");
                                                   code = codeFieldSplit[codeFieldSplit.length - 1];

                                                   GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {
                                                       @Override
                                                       protected void onPostExecute(Boolean response) {
                                                           if (response) {
                                                               Toast.makeText(WebViewActivity.this, "Access token true", Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               Toast.makeText(WebViewActivity.this, "Access token false", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   };
                                                   getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), service, code, "", "");
                                                  // Intent intent = new Intent(WebViewActivity.this, NavigatorActivity.class);
                                                  // intent.putExtra("user", currentUser);
                                                  // startActivity(intent);
                                                   finish();
                                               }

                                           } else if (service.equals("onedrive")) {
                                               if (originalUrl.contains("https://www.unibox.com/?code") && control==true) {
                                                    control=false;
                                                   String code;
                                                   String[] splittedString = originalUrl.split("=");
                                                   code = splittedString[1];
                                                   originalUrl="";
                                                   System.out.println("CCCC "+code);
                                                   GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {
                                                       @Override
                                                       protected void onPostExecute(Boolean response) {
                                                           if (response) {
                                                               Toast.makeText(WebViewActivity.this, "Access token true", Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               Toast.makeText(WebViewActivity.this, "Access token false", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   };
                                                   getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), service, code, "", "");
                                                   //Intent intent = new Intent(WebViewActivity.this, NavigatorActivity.class);
                                                   //intent.putExtra("user", currentUser);
                                                   //startActivity(intent);
                                                   finish();
                                               }
                                           }
                                       }
                                   }
        );
        myWebView.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
