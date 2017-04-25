package com.example.patrick.visiturs;

import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Patrick on 07-03-2017.
 */

public class ListOfLocations extends ListActivity {
    CustomListView clv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clv = new CustomListView(this, R.layout.customlocationcell, MainActivity.locate);
        this.setListAdapter(clv);
    }
}
