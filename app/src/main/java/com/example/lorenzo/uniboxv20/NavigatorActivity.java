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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.CreateFolderTask;
import com.example.lorenzo.uniboxv20.util.DirectoryListAdapter;
import com.example.lorenzo.uniboxv20.util.GetAccessTokenTask;
import com.example.lorenzo.uniboxv20.util.GetDirectoryListTask;

import java.util.ArrayList;


public class NavigatorActivity extends Activity {

    private User currentUser;
    private String currentPath = "";
    private ListView listView;
    private DirectoryListAdapter arrayAdapter;
    private ArrayList<String> dirList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        listView = (ListView) findViewById(R.id.listView);

        currentUser = (User) getIntent().getExtras().getSerializable("user");
        GetDirectoryListTask task = new GetDirectoryListTask(currentUser) {
            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                try {
                    Toast.makeText(NavigatorActivity.this, strings.get(0), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(NavigatorActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
                dirList = strings;
                setListAdapter();
            }
        };
        task.execute(currentPath);

    }

    private void setListAdapter() {

        // Se sto visitando la radice non serve il Back, quindi non lo aggiungo
        if (!currentPath.equals("/")) {
            dirList.add(0, "Back");
        }

        // setto l'adapter
        arrayAdapter = new DirectoryListAdapter(NavigatorActivity.this, R.layout.listview_layout, dirList);
        listView.setAdapter(arrayAdapter);

        // setto cosa fare in caso di click sull'item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Se ho cliccato su Back
                if (adapterView.getItemAtPosition(position).toString().equals("Back")) {

                    GetDirectoryListTask backTask = new GetDirectoryListTask(currentUser) {

                        @Override
                        protected void onPostExecute(ArrayList<String> strings) {
                            dirList = strings;

                            setListAdapter();
                        }
                    };
                    // tolgo da currentPath l'ultima directory
                    String[] splittedString = currentPath.split("/");
                    currentPath = "";
                    for (int i = 0; i < splittedString.length - 2; i++) {
                        currentPath += splittedString[i] + "/";
                    }
                    backTask.execute(currentPath);
                } else {
                    GetDirectoryListTask t = new GetDirectoryListTask(currentUser) {
                        @Override
                        protected void onPostExecute(ArrayList<String> strings) {
                            dirList = strings;
                            setListAdapter();
                        }
                    };
                    currentPath = currentPath + adapterView.getItemAtPosition(position).toString();
                    t.execute(currentPath);
                    currentPath += "/";
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(NavigatorActivity.this, "menu", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void onDeleteButtonClick(int position) {
        // TODO implementare il Delete di un file/directory
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add_service) {
            Intent intent = new Intent(this, AddServiceActivity.class);
            intent.putExtra("user", currentUser);
            startActivity(intent);
        }
        if (id == R.id.uploadFile) {
            Intent intent = new Intent(this, FileExploreActivity.class);
            startActivity(intent);
        }
        if(id == R.id.refresh){
            refresh();
        }
        if (id == R.id.createFolder) {

            //Preparing views
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_foldername, null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
            final EditText nameText = (EditText) layout.findViewById(R.id.nameText);

            //Building dialog
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    final String name = nameText.getText().toString();

                   CreateFolderTask createFolderTask = new CreateFolderTask(){
                       @Override
                        protected  void onPostExecute(Boolean result){
                           if(result){
                               Toast.makeText(NavigatorActivity.this, "folder created", Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(NavigatorActivity.this, "false", Toast.LENGTH_SHORT).show();
                           }
                       }
                   };

                    createFolderTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath, name);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(){
        GetDirectoryListTask task = new GetDirectoryListTask(currentUser) {
            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                try {
                    Toast.makeText(NavigatorActivity.this, strings.get(0), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(NavigatorActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
                dirList = strings;
                setListAdapter();
            }
        };
        task.execute(currentPath);
    }


}