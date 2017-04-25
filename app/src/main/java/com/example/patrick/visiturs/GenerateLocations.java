package com.example.patrick.visiturs;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by Patrick on 07-03-2017.
 */

public class GenerateLocations {
    private static ArrayList<Location> locate;
    private static GenerateLocations instance = null;
    private static int id = 1;
    private static DAL dal;

    private GenerateLocations() {

    }

    public static GenerateLocations getInstace(Context context)
    {
        dal = new DAL(context);
        if( instance == null)
        {
            instance = new GenerateLocations();
        }
        return instance;
    }
    public ArrayList<Location> getLocation()
    {
        return locate;
    }

    public ArrayList<Location> AddLocation(Location location)
    {
        dal.insert(location);
        return dal.selectAll();
    }

    public Location editLocation(Location location)
    {
        for(int i = 0; i < locate.size(); i++)
        {
            Location ll = locate.get(i);
            if(ll.getId() == location.getId())
            {
                locate.remove(i);
                locate.add(i,location);
                return locate.get(i);
            }

        }
        return null;
    }
    public Location getLocationByID(int id)
    {
        for (Location l:dal.selectAll()) {
            if(l.getId()==id)
            {
                return l;
            }
        }
        return null;
    }



}
