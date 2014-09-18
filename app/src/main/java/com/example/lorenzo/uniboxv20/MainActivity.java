package com.example.lorenzo.uniboxv20;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.LoginTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private EditText emailET;
    private EditText passwordET;
    private Button loginBtn;
    private Button signupBtn;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signupBtn = (Button) findViewById(R.id.signupButton);

        // ------------------------------------------------------------------------------
        emailET.setText("lory90@gmail.com");
        passwordET.setText("ie810eal");
        // ------------------------------------------------------------------------------

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginBtn.setEnabled(false);
                signupBtn.setEnabled(false);
                String[] stringArray = new String[2];
                stringArray[0] = emailET.getText().toString();
                stringArray[1] = passwordET.getText().toString();
                LoginTask loginTask = new LoginTask() {
                    @Override
                    protected void onPostExecute(User user) {
                        if(user != null){
                            currentUser = user;
                            Intent intent = new Intent(getApplicationContext(), NavigatorActivity.class);
                            intent.putExtra("user", currentUser);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                        loginBtn.setEnabled(true);
                        signupBtn.setEnabled(true);
                    }
                };
                loginTask.execute(stringArray);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationFormActivity.class);
                startActivity(intent);
            }
        });

    }// Fine onCreate



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

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
