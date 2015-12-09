package com.swatiag1101.imdb.ActivityPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.swatiag1101.imdb.Adapter.CustomAdapter;
import com.swatiag1101.imdb.ListItems;
import com.swatiag1101.imdb.R;
import com.swatiag1101.imdb.RequestResponse.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Latest_Movies extends ActionBarActivity implements OnItemClickListener{

    private static final String KEY_NAME = "original_title";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "poster_path";
    ListView lv;
    ArrayList<ListItems> al = new ArrayList<ListItems>();

    CustomAdapter ca;
    private static String url = "http://api.themoviedb.org/";
    private static String api_ver = "3";
    private static String context_path = "movie/now_playing";
    private static String api_key = "8496be0b2149805afa458ab8ec27560c";
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv= (ListView) findViewById(R.id.list_of_latest_movies);

        AsyncTaskForLatestMovies asyncTaskForLatestMovies = new AsyncTaskForLatestMovies();
        asyncTaskForLatestMovies.execute();

        lv.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       // if(id==R.id.voteasc){
         //   AsyncTaskForLatestMoviesAsc as = new AsyncTaskForLatestMoviesAsc();
         //   as.execute();
       // }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(Latest_Movies.this,DetailsOfMovies.class);
        ListItems l = (ListItems) ca.getItem(position);
        long id_movie = l.getId();
        Log.d("movie id",""+id_movie);
        i.putExtra("id",id_movie);
        startActivity(i);

    }

    class AsyncTaskForLatestMovies extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            RequestHandler requestHandler = new RequestHandler(url+api_ver+"/"+context_path+"?api_key="+api_key);
            String JSONResponseStr = requestHandler.getResponse();
            Log.d("JSON",JSONResponseStr);

            try {
                JSONObject jsonObject = new JSONObject(JSONResponseStr);
                JSONArray results = jsonObject.getJSONArray("results");

                for(int i=0;i<results.length();i++){

                    JSONObject j = results.getJSONObject(i);

                    String name = j.getString(KEY_NAME);
                    String capital = j.getString(KEY_RELEASE_DATE);
                    String population = j.getString(KEY_VOTE_AVERAGE);
                    String vote_count = j.getString(KEY_VOTE_COUNT);
                    long id= j.getLong(KEY_ID);
                    String image = j.getString(KEY_IMAGE);

                    ListItems li = new ListItems();
                    li.setTitle(name);
                    li.setReleaseDate(capital);
                    li.setVote(population);
                    li.setVote_count(vote_count);
                    li.setId(id);
                    li.setUrl(image);

                    al.add(li);

                    Log.d(KEY_NAME, name);
                    Log.d(KEY_RELEASE_DATE,capital);
                    Log.d(KEY_VOTE_AVERAGE,population);
                    Log.d(KEY_VOTE_COUNT,vote_count);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Array size",""+al.size());
            ca = new CustomAdapter(Latest_Movies.this,al);
            lv.setAdapter(ca);

        }
    }


}
