package com.example.lorenzo.uniboxv20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.AddServiceTask;
import com.example.lorenzo.uniboxv20.util.LoginTask;


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
    }

    public void onClick(View view) {

        String[] stringArray = new String[3];
        stringArray[0] = currentUser.getEmail();
        stringArray[1] = currentUser.getAccessToken();
        stringArray[2] = (String) view.getTag();
        message = stringArray[2];

        for(ImageButton i : imageButton){
            i.setEnabled(false);
        }

        AddServiceTask serviceTask = new AddServiceTask() {
            @Override
            protected void onPostExecute(String result) {
               if(result != null) {
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
