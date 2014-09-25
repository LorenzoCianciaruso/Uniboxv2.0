package com.example.lorenzo.uniboxv20;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lorenzo.uniboxv20.data.User;
import com.example.lorenzo.uniboxv20.util.CreateFolderTask;
import com.example.lorenzo.uniboxv20.util.DeleteFolderOrFileTask;
import com.example.lorenzo.uniboxv20.util.DirectoryListAdapter;
import com.example.lorenzo.uniboxv20.util.GetAccessTokenTask;
import com.example.lorenzo.uniboxv20.util.GetDirectoryListTask;
import com.example.lorenzo.uniboxv20.util.GetLinkTask;
import com.example.lorenzo.uniboxv20.util.ShareByEmailTask;
import com.example.lorenzo.uniboxv20.util.ShareSocialTask;

import java.util.ArrayList;


public class NavigatorActivity extends Activity {

    private User currentUser;
    private String currentPath = "/";
    private ListView listView;
    private DirectoryListAdapter arrayAdapter;
    private ArrayList<String> dirList = new ArrayList<String>();
    private String selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
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

                selectedItem = adapterView.getItemAtPosition(position).toString();

                // Se ho cliccato su Back
                if (adapterView.getItemAtPosition(position).toString().equals("Back")) {

                    GetDirectoryListTask backTask = new GetDirectoryListTask(currentUser) {

                        @Override
                        protected void onPostExecute(ArrayList<String> strings) {
                            dirList = strings;

                            setListAdapter();
                        }
                    };

                    int index = currentPath.lastIndexOf("/");
                    if(index==0){
                        currentPath="/";
                    }else {

                        currentPath = currentPath.substring(0, currentPath.length() - 2);

                        index = currentPath.lastIndexOf("/");
                        currentPath = currentPath.substring(0, index);

                    }

                    // tolgo da currentPath l'ultima directory
                   /* String[] splittedString = currentPath.split("/");
                    currentPath = "/";
                    for (int i = 1; i < splittedString.length - 2; i++) {
                        currentPath += splittedString[i] + "/";
                    }*/
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
        if (id == R.id.logout) {
            Intent intent = new Intent(NavigatorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_add_service) {
            Intent intent = new Intent(this, AddServiceActivity.class);
            intent.putExtra("user", currentUser);
            startActivity(intent);
        }
        if (id == R.id.uploadFile) {
            Intent intent = new Intent(this, FileExploreActivity.class);
            intent.putExtra("user", currentUser);
            intent.putExtra("remotePath", currentPath);
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

                dirList = strings;
                setListAdapter();
            }
        };
        task.execute(currentPath);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        selectedItem = "/" + (String) listView.getItemAtPosition(position);
        switch(item.getItemId()) {
            case R.id.downloadFile:
                return true;
            case R.id.getLink:
                GetLinkTask task = new GetLinkTask() {
                    @Override
                    protected void onPostExecute(String strings) {
                            Toast.makeText(NavigatorActivity.this, strings, Toast.LENGTH_SHORT).show();
                    }
                };
                task.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath + selectedItem);
                return true;
            case R.id.delete:
                DeleteFolderOrFileTask deleteTask = new DeleteFolderOrFileTask() {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(NavigatorActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                deleteTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath + selectedItem);
                return true;
            case R.id.shareEmail:
                ShareByEmailTask emailTask = new ShareByEmailTask(){
                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(NavigatorActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                String[][] parameters = new String[2][];
                String[] credentials = new String[3];
                String[] addresses = new String [1];

                credentials[0] = currentUser.getEmail();
                credentials[1] = currentUser.getAccessToken();
                credentials[2] = currentPath + selectedItem;
                addresses[0] = "lory90@gmail.com";
                parameters[0] = credentials;
                parameters[1] = addresses;
                emailTask.execute(credentials, addresses);
                return true;
            case R.id.shareFacebook:
                ShareSocialTask socialTask = new ShareSocialTask() {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(NavigatorActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                socialTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath + selectedItem, "facebook");
                return true;
            case R.id.shareTwitter:
                socialTask = new ShareSocialTask() {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(NavigatorActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                socialTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath + selectedItem, "twitter");
                return true;
            case R.id.shareYoutube:
                socialTask = new ShareSocialTask() {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(NavigatorActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                socialTask.execute(currentUser.getEmail(), currentUser.getAccessToken(), currentPath + selectedItem, "youtube");
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


}