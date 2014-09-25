package com.example.lorenzo.uniboxv20.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lorenzo.uniboxv20.NavigatorActivity;
import com.example.lorenzo.uniboxv20.R;

import java.util.List;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class DirectoryListAdapter extends ArrayAdapter<String> {

    private Context context;

    public DirectoryListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_layout, null);
        }

        String item = getItem(position);
        if (item != null) {

            TextView dirTextView = (TextView) view
                    .findViewById(R.id.listTextView);
           // Button deleteBtn = (Button) view.findViewById((R.id.deleteButton));

            // setto il titolo
            if (dirTextView != null) {
                dirTextView.setText(item);
            }

            // setto l'immagine
           /* if (deleteBtn != null) {
                if (item.equals("Back")) {
                    deleteBtn.setVisibility(View.GONE);
                    return view;
                }
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((NavigatorActivity) context).onDeleteButtonClick(position);
                    }
                });
            }*/
        }
        return view;
    }
}

