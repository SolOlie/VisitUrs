package com.example.patrick.visiturs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Patrick on 07-03-2017.
 */

public class CustomListView extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Location> locate = null;
    private Location l;
    public CustomListView(Context context, int resource, ArrayList<Location> locate) {
        super(context, resource, locate);
        this.context = context;
        this.resource = resource;
        this.locate = locate;


    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        l = locate.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customlocationcell, parent, false);
        }
       TextView name = (TextView) convertView.findViewById(R.id.txtView);
        name.setText(l.getName());
        return convertView;
    }
}




