package com.swatiag1101.imdb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.util.Log;
import android.widget.TextView;

import com.swatiag1101.imdb.ListItems;
import com.swatiag1101.imdb.ListSearch;
import com.swatiag1101.imdb.R;

import java.util.ArrayList;

/**
 * Created by Swati Agarwal on 03-08-2015.
 */
public class CustomAdapterForSearch extends BaseAdapter {

    private Activity context;
    ArrayList<ListSearch> al;
    LayoutInflater inflater;

    public CustomAdapterForSearch(Activity context, ArrayList<ListSearch> al) {
        this.context = context;
        this.al = al;
    }

    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            // convertView = new View(context);
            convertView = inflater.inflate(R.layout.listview_search, null);

            ListSearch listing = al.get(position);
            ((TextView) convertView.findViewById(R.id.textView1)).setText(listing.getName());

        }
        return null;
    }
}
