package com.example.patrick.visiturs;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Patrick on 19-04-2017.
 */

public class GeoCoder {
    private ArrayList<Positions> pos;
    private OnLoad onload;
    private String URL;
    private Positions positions;
    AsyncHttpClient client;

    public GeoCoder(OnLoad onload, Location locat) {
       pos = new ArrayList<>();
        this.onload = onload;
        String realAddress = makeAddress(locat);
        URL = "https://maps.googleapis.com/maps/api/geocode/json?address="+realAddress+"&key=AIzaSyC7s6ZPiuA_IkMqkWaxUj2unzbmvrkGldE";
        client = new AsyncHttpClient();
    }

    private String makeAddress(Location locate) {
        //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
        String s = locate.address.trim() + " " + locate.number.trim()+", "+locate.zipcode.trim();
        return s;
    }

    public double getLat()
    {

        return pos.get(0).getLat();

    }
    public double getLon()
    {

        return pos.get(0).getLon();

    }

    public void loadAll(){
        client.get(URL, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                jsonStringToBusinessEntities(new String(responseBody));
                onload.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.d("fejl 40", "Did not work");
            }
        });
    }
    private void jsonStringToBusinessEntities(String result)
    {
        if (result.startsWith("error"))
        {

            return;
        }

        if (result == null)
        {

            return;
        }
        JSONArray array = null;
        try {
            JSONObject object = new JSONObject(result);

            double lng = ((JSONArray)object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            double lat = ((JSONArray)object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            pos.add(new Positions(lat, lng));

            //array = new JSONArray(result);


           // for (int i = 0; i < array.length(); i++) {
           //     pos.add(new Positions(array.getJSONObject(i).getDouble("lat"), array.getJSONObject(i).getDouble("lng")));
           // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
