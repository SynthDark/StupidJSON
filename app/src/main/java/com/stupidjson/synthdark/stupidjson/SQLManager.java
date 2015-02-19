package com.stupidjson.synthdark.stupidjson;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class SQLManager extends SQLiteOpenHelper {
    private static String DB_NAME = "ImageDatabase";
    private static int DB_VERSION = 1;

    private final Context myContext;
    private final MainActivity mainActivity;


    public SQLManager(Context context, MainActivity mainActivity) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE `Images` (`ID` INTEGER NOT NULL PRIMARY KEY, `imageID` INTEGER NOT NULL, `Title` TEXT NOT NULL, `UserID` INTEGER NOT NULL, `UserName` TEXT NOT NULL);";
        db.execSQL(query);

        mainActivity.ExecuteJSONReader();
    }

    public void ImportDatabase(List<Images> imagesList) {
        for (int i=0; i<imagesList.size(); i++) {
            String query = "INSERT INTO `Images` VALUES('" + imagesList.get(i).ID + "','" + imagesList.get(i).ImageID + "'," + DatabaseUtils.sqlEscapeString(imagesList.get(i).Title) + ",'" + imagesList.get(i).UserID + "'," + DatabaseUtils.sqlEscapeString(imagesList.get(i).UserName) + ");";
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(query);
        }
    }

    public List<Images> LoadImageData() {
        String query = "SELECT * FROM Images;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<Images> imageList = new ArrayList<Images>();
        if (cursor.moveToFirst()) {
            do {
                Images newImage = new Images();
                List<String> imageDataList = new ArrayList<String>();
                for (int i = 0; i<5; i++) {
                    imageDataList.add(cursor.getString(i));
                }
                newImage.ID = Integer.parseInt(imageDataList.get(0).toString());
                newImage.ImageID = Integer.parseInt(imageDataList.get(1).toString());
                newImage.Title = imageDataList.get(2).toString();
                newImage.UserID = Integer.parseInt(imageDataList.get(3).toString());
                newImage.UserName = imageDataList.get(4).toString();
                imageList.add(newImage);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return imageList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
