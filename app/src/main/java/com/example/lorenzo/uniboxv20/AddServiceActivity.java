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

    private ImageButton[] imageButton = new ImageButton[7];
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
                try {
                    Toast.makeText(AddServiceActivity.this, availableServices.get(0), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(AddServiceActivity.this, "null", Toast.LENGTH_SHORT).show();
                }

                //if (availableServices.contains("Dropbox")) {
                if(true){
                    imageButton[0].setImageResource(R.drawable.dropbox);
                    imageButton[0].setEnabled(true);
                }
               // if (availableServices.contains("Box")) {
                    if(true){
                    imageButton[1].setImageResource(R.drawable.box);
                    imageButton[1].setEnabled(true);
                }
                //if (availableServices.contains("Mega")) {
                    if(true){
                    imageButton[2].setImageResource(R.drawable.mega);
                    imageButton[2].setEnabled(true);
                }
                //if (availableServices.contains("Facebook")) {
                if(true){
                    imageButton[3].setImageResource(R.drawable.facebook);
                    imageButton[3].setEnabled(true);
                }
                //if (availableServices.contains("Twitter")) {
                if(true){
                    imageButton[4].setImageResource(R.drawable.twitter);
                    imageButton[4].setEnabled(true);
                }
                //if (availableServices.contains("Youtube")) {
                if(true){
                    imageButton[5].setImageResource(R.drawable.youtube);
                    imageButton[5].setEnabled(true);
                }
                //if (availableServices.contains("OneDrive")) {
                if(true){
                    imageButton[6].setImageResource(R.drawable.onedrive);
                    imageButton[6].setEnabled(true);
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
        imageButton[6] = (ImageButton) findViewById(R.id.imageButton7);

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

                    final String user = usernameText.getText().toString();
                    final String password = passwordText.getText().toString();

                    AddServiceTask addServiceTask = new AddServiceTask() {
                        @Override
                        protected void onPostExecute(String string) {

                                GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask() {

                                    @Override
                                    protected void onPostExecute(Boolean result1) {
                                        if (result1) {
                                            Toast.makeText(AddServiceActivity.this, "AccToken mega OK", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(AddServiceActivity.this, NavigatorActivity.class);
                                            intent.putExtra("user", currentUser);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AddServiceActivity.this, "false", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                getAccessTokenTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), message, "", user, password);
                        }
                    };
                    addServiceTask.execute(currentUser.getEmail(),currentUser.getAccessToken(),message);
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
        //finish();
    }

}
