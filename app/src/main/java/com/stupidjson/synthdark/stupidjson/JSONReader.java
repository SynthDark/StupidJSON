package com.stupidjson.synthdark.stupidjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.Display;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONReader extends AsyncTask<String, String, Void> {

    MainActivity mainActivity;
    List<Images> imageList;

    InputStream inputStream = null;

    public JSONReader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        imageList = new ArrayList<Images>();
    }

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... params) {

        String url_select = "http://challenge.superfling.com";
        try {
            URL u = new URL(url_select);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(30000);
            c.setReadTimeout(30000);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    String result = sb.toString();

                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<List<Images>>(){}.getType();
                    imageList = gson.fromJson(result, collectionType);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

    protected void onPostExecute(Void v) {
        mainActivity.JSONPostExecute(imageList);
    }
}
