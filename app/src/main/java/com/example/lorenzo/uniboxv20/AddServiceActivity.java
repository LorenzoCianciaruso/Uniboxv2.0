package com.example.lorenzo.uniboxv20;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.AddServiceTask;
import com.example.lorenzo.uniboxv20.util.AvailableServiceTask;
import com.example.lorenzo.uniboxv20.util.GetAccessTokenTask;
import com.example.lorenzo.uniboxv20.util.LoginTask;

import java.util.ArrayList;


public class AddServiceActivity extends Activity {

    private ImageButton[] imageButton = new ImageButton[6];
    private User currentUser;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        currentUser = (User) getIntent().getExtras().getSerializable("user");

        findViewById();

        AvailableServiceTask availableServiceTask = new AvailableServiceTask() {

            @Override
            protected void onPostExecute(ArrayList<String> availableServices) {
                for(String i : availableServices) {
                    if (i.equals("Dropox")) {
                        imageButton[0].setImageResource(R.drawable.dropbox);
                        imageButton[0].setEnabled(true);
                    }
                    else if (i.equals("Box")) {
                        imageButton[1].setImageResource(R.drawable.box);
                        imageButton[1].setEnabled(true);
                    }
                    else if (i.equals("Facebook")) {
                        imageButton[2].setImageResource(R.drawable.mega);
                        imageButton[2].setEnabled(true);
                    }
                    else if (i.equals("Facebook")) {
                        imageButton[3].setImageResource(R.drawable.facebook);
                        imageButton[3].setEnabled(true);
                    }
                    else if (i.equals("Twitter")) {
                        imageButton[4].setImageResource(R.drawable.twitter);
                        imageButton[4].setEnabled(true);
                    }
                    else if (i.equals("Youtube")) {
                        imageButton[5].setImageResource(R.drawable.youtube);
                        imageButton[5].setEnabled(true);
                    }
                }
            }
        };
        availableServiceTask.execute(currentUser.getEmail(), currentUser.getAccessToken());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
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

    private void findViewById() {
        imageButton[0] = (ImageButton) findViewById(R.id.imageButton);
        imageButton[1] = (ImageButton) findViewById(R.id.imageButton2);
        imageButton[2] = (ImageButton) findViewById(R.id.imageButton3);
        imageButton[3] = (ImageButton) findViewById(R.id.imageButton4);
        imageButton[4] = (ImageButton) findViewById(R.id.imageButton5);
        imageButton[5] = (ImageButton) findViewById(R.id.imageButton6);

        for (ImageButton i : imageButton) {
            i.setEnabled(false);
        }
    }

    public void onClick(View view) {

        String[] stringArray = new String[3];
        stringArray[0] = currentUser.getEmail();
        stringArray[1] = currentUser.getAccessToken();
        stringArray[2] = (String) view.getTag();
        message = stringArray[2];

        for (ImageButton i : imageButton) {
            i.setEnabled(false);
        }

        if (message.equals("mega")) {
            //Preparing views
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_signin, null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
            final EditText usernameText = (EditText) layout.findViewById(R.id.username);
            final EditText passwordText = (EditText) layout.findViewById(R.id.password);

            //Building dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {

                    String user = usernameText.getText().toString();
                    String password = passwordText.getText().toString();

                    GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask(){

                        @Override
                        protected void onPostExecute(Boolean result) {
                            if (result) {
                                dialog.dismiss();
                                Intent intent = new Intent(AddServiceActivity.this, NavigatorActivity.class);
                                intent.putExtra("user", currentUser);
                                startActivity(intent);
                            }
                        }
                    };
                    getAccessTokenTask.execute(currentUser.getEmail(),currentUser.getAccessToken(), message, "", user, password);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            AddServiceTask serviceTask = new AddServiceTask() {
                @Override
                protected void onPostExecute(String result) {
                    if (result != null) {
                        Intent intent = new Intent(AddServiceActivity.this, WebViewActivity.class);
                        intent.putExtra("url", result);
                        intent.putExtra("service", message);
                        intent.putExtra("user", currentUser);
                        startActivity(intent);
                    }

                }
            };
            serviceTask.execute(stringArray);
        }
    }

}
