package com.stupidjson.synthdark.stupidjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    MainActivity mainActivity;
    Context context;
    private static LayoutInflater inflater = null;
    List<Images> imageList;

    public CustomAdapter(MainActivity mainActivity, List<Images> imageList) {
        this.mainActivity = mainActivity;
        context = mainActivity;
        this.imageList = imageList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("KEK");

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.image_list, null);
        holder.textView=(TextView) rowView.findViewById(R.id.textViewImage);
        holder.imageView=(ImageView) rowView.findViewById(R.id.imageViewImage);
        holder.textView.setText(imageList.get(position).Title);
        if (imageList.get(position).image != null) {
            holder.imageView.setImageBitmap(imageList.get(position).image);
        }
        return rowView;
    }
}
