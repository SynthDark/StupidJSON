package com.stupidjson.synthdark.stupidjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.view.Display;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageDownloader extends AsyncTask<String, String, Void> {

    MainActivity mainActivity;
    int imageID;
    int screenWidth;
    int screenHeight;
    Bitmap image;
    int id;


    public ImageDownloader(MainActivity mainActivity, int id, int imageID) {
        this.mainActivity = mainActivity;
        this.imageID = imageID;
        this.id = id;

        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... params) {

        String url_select = "http://challenge.superfling.com/photos/" + imageID;
        int inSampleSize = 1;

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
                    InputStream input = c.getInputStream();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(input, null, options);

                    if (options.outHeight > screenHeight || options.outWidth > screenWidth) {
                        System.out.println("SAMPLING");
                        final int halfHeight = options.outHeight / 2;
                        final int halfWidth = options.outWidth / 2;

                        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                        // height and width larger than the requested height and width.
                        while ((halfHeight / inSampleSize) > screenHeight
                                && (halfWidth / inSampleSize) > screenWidth) {
                            inSampleSize *= 2;
                        }
                    }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    InputStream input = c.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = (inSampleSize * 10);
                    image = BitmapFactory.decodeStream(input, null, options);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    protected void onPostExecute(Void v) {
        mainActivity.AddImage(id, image);
    }
}
