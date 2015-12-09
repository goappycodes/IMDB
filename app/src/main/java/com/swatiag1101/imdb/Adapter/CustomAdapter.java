package com.swatiag1101.imdb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swatiag1101.imdb.ListItems;
import com.swatiag1101.imdb.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Swati Agarwal on 01-08-2015.
 */
public class CustomAdapter extends BaseAdapter {
    private Activity context;
    ArrayList<ListItems> al;
    LayoutInflater inflater;
    ImageView imageView;
    public CustomAdapter(Activity c,ArrayList<ListItems> li){
        context = c;
        al=li;
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
        return  position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            // convertView = new View(context);
            convertView = inflater.inflate(R.layout.listview_layout, null);

            ListItems listing = al.get(position);
            GetXMLTask getXMLTask = new GetXMLTask();
            String URL ="http://image.tmdb.org/t/p/w500"+listing.getUrl();
            getXMLTask.execute(new String[]{URL});

            ((TextView) convertView.findViewById(R.id.title)).setText(listing.getTitle());
            ((TextView) convertView.findViewById(R.id.release)).setText("Release on: "+listing.getReleaseDate());
            ((TextView) convertView.findViewById(R.id.popularity)).setText("Popularity: "+listing.getId());
            ((ImageView) convertView.findViewById(R.id.star)).setImageResource(R.drawable.blue_star);
            ((TextView) convertView.findViewById(R.id.vote)).setText("(" + listing.getVote() + "/10) voted by " + listing.getVote_count() + " users");
            imageView = (ImageView) convertView.findViewById(R.id.poster);
        }
        //al.add(listing);
        return convertView;

    }


    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                if(stream!=null)
                    stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
