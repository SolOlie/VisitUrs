package com.example.patrick.visiturs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lw;
    public static ArrayList<Location> locate = new ArrayList<>();
    private CustomListView clv;
    private DAL dal;
    private Location l;
    private int i;
    private CardView cardView;
    private GestureDetector gestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dal = new DAL(this);
        locate = dal.selectAll();
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Locations");

        clv = new CustomListView(this, R.layout.customlocationcell, locate);
        lw = (ListView) findViewById(R.id.lstView);
        lw.setAdapter(clv);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactListClick(position);
            }
        });
            lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    MakeCall(position);

                    return true;
                }
            });



    }
    private void init() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add)
        {
            Intent i = new Intent(this, DetailsView.class);
            i.putExtra("LocationID", 0);
            startActivityForResult(i, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       refreshList();
    }

    private void refreshList() {
        locate = dal.selectAll();
        clv = new CustomListView(this, R.layout.customlocationcell, locate);
        lw.setAdapter(clv);
    }

    public void ContactListClick(int i) {
        Location l = locate.get(i);
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra("LocationID", l.getId());
        startActivityForResult(intent,1);
    }
    public void MakeCall(int i){
        Location l = locate.get(i);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + l.getPhoneNumber()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


}
