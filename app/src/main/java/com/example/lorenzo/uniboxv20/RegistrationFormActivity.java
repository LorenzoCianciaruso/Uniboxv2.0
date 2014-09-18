package com.example.lorenzo.uniboxv20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.LoginTask;
import com.example.lorenzo.uniboxv20.util.RegisterNewAccountTask;


public class RegistrationFormActivity extends Activity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private Button register;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        name = (EditText) findViewById(R.id.nameText);
        surname = (EditText) findViewById(R.id.surnameText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        register = (Button) findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        register.setEnabled(false);
                        String[] stringArray = new String[4];
        stringArray[0] = email.getText().toString();
        stringArray[1] = password.getText().toString();
        stringArray[2] = name.getText().toString();
        stringArray[3] = surname.getText().toString();

        RegisterNewAccountTask registerTask = new RegisterNewAccountTask() {
            @Override
            protected void onPostExecute(User user) {
                if(user != null){
                    currentUser = user;
                    Intent intent = new Intent(getApplicationContext(), NavigatorActivity.class);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistrationFormActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    register.setEnabled(true);
                }
            }
        };
        registerTask.execute(stringArray);
    }
});
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration_form, menu);
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
