package com.example.patrick.visiturs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 23-03-2017.
 */

public class DAL {
    private static final String DATABASE_NAME = "VisitUrs.db";
    private static final int DATABASE_VERSION = 11;
    private static final String TABLE_NAME = "Location";

    private Context context;

    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private static final String INSERT = "insert into " + TABLE_NAME
            + " (name, address, phoneNumber, email, lat, lon, zipcode, number, imagePath) values (?,?,?,?,?,?,?,?,?)";

    public DAL(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public long insert(Location l) {
        this.insertStmt.bindString(1, l.getName());
        this.insertStmt.bindString(2, l.getAddress());
        this.insertStmt.bindString(3, l.getPhoneNumber());
        this.insertStmt.bindString(4, l.getEmail());
        this.insertStmt.bindString(5, l.getLat()+"");
        this.insertStmt.bindString(6, l.getLon()+"");
        this.insertStmt.bindString(7, l.getZipcode());
        this.insertStmt.bindString(8, l.getNumber());
        if(l.getImagePath() == null)
        {
            l.setImagePath("");
        }
        this.insertStmt.bindString(9, l.getImagePath());
        return this.insertStmt.executeInsert();
    }

    public void deleteLocation(int id) {
        String ids = id+"";
        db.delete(TABLE_NAME, "id=?", new String[]{ids});
    }
    public void updateLocation(Location location, int id)
    {
        ContentValues cv = new ContentValues();
        cv.put("name", location.getName() );
        cv.put("email", location.getEmail() );
        cv.put("phoneNumber", location.getPhoneNumber() );
        cv.put("address", location.getAddress() );
        cv.put("lat", location.getLat()+"");
        cv.put("lon", location.getLon()+"");
        cv.put("zipcode", location.getZipcode());
        cv.put("number", location.getNumber());
        if(location.getImagePath() == null)
        {
            location.setImagePath("");
        }
        cv.put("imagePath", location.getImagePath());
        db.update(TABLE_NAME, cv, "id=?", new String[]{id+""});
    }

    public ArrayList<Location> selectAll() {
        ArrayList<Location> list = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "name", "address", "zipcode", "number", "phoneNumber", "email", "lat", "lon", "imagePath" },
                null, null, null, null, "name desc");
        if (cursor.moveToFirst()) {
            do {
                Location l = new Location(cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(0), cursor.getString(6));
                l.setLon(Double.parseDouble(cursor.getString(8)));
                l.setLat(Double.parseDouble(cursor.getString(7)));
                l.setImagePath(cursor.getString(9));
                list.add(l);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }




    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT,  address TEXT, phoneNumber TEXT, email TEXT, lat TEXT, lon TEXT, zipcode TEXT, number TEXT, imagePath TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
