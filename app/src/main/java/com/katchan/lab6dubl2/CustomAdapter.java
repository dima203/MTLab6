package com.koverets.lab6dubl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] songTitles;
    private int[] songImages;

    public CustomAdapter(Context context, String[] songTitles, int[] songImages) {
        super(context, R.layout.list_item, songTitles);
        this.context = context;
        this.songTitles = songTitles;
        this.songImages = songImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        ImageView imageView = rowView.findViewById(R.id.song_image);
        TextView textView = rowView.findViewById(R.id.song_title);

        imageView.setImageResource(songImages[position]);
        textView.setText(songTitles[position]);

        return rowView;
    }
}

