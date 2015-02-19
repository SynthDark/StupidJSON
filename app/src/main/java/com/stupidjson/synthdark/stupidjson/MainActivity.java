package com.stupidjson.synthdark.stupidjson;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    List<Images> imageList;

    SQLManager sqlManager;
    JSONReader jsonReader;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlManager = new SQLManager(this, this);
        LoadImages();

        setContentView(R.layout.activity_main);
    }

    public void ExecuteJSONReader() {
        jsonReader = new JSONReader(this);
        jsonReader.execute();
    }

    public void JSONPostExecute (List<Images> imageList){
        sqlManager.ImportDatabase(imageList);
        LoadImages();
    }

    public void LoadImages() {
        imageList = sqlManager.LoadImageData();

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomAdapter(this, imageList));

        for (int i=0; i<imageList.size(); i++) {
            System.out.println(imageList.get(i).ID + ", " + imageList.get(i).ImageID + ", " + imageList.get(i).Title + ", " + imageList.get(i).UserID + ", " + imageList.get(i).UserName);
            ImageDownloader imageDownloader = new ImageDownloader(this, i, imageList.get(i).ImageID);
            imageDownloader.execute();
        }
    }

    public void AddImage (int i, Bitmap image){
        imageList.get(i).image = image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
