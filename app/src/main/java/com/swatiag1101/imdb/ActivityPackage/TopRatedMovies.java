package com.swatiag1101.imdb.ActivityPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.swatiag1101.imdb.Adapter.CustomAdapter;
import com.swatiag1101.imdb.ListItems;
import com.swatiag1101.imdb.R;
import com.swatiag1101.imdb.RequestResponse.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopRatedMovies extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView lv_top;
    CustomAdapter ca_top;
    ArrayList<ListItems> al_top=new ArrayList<ListItems>();
    private static String url = "http://api.themoviedb.org/";
    private static String api_ver = "3";
    private static String context_path = "movie/upcoming";
    private static String api_key = "8496be0b2149805afa458ab8ec27560c";
    private static String url_cum = url+api_ver+"/"+context_path+"?api_key="+api_key;
    private static final String KEY_NAME = "original_title";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "poster_path";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);

        lv_top = (ListView) findViewById(R.id.list_of_toprated_movies);

        AsyncTaskForTopRatedMovies asyncTaskForTopRatedMovies = new AsyncTaskForTopRatedMovies();
        asyncTaskForTopRatedMovies.execute();

        lv_top.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_rated_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(TopRatedMovies.this,DetailsOfMovies.class);
        ListItems l = (ListItems) ca_top.getItem(position);
        long id_movie = l.getId();
        Log.d("movie id",""+id_movie);
        i.putExtra("id",id_movie);
        startActivity(i);
    }

    class AsyncTaskForTopRatedMovies extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            RequestHandler req = new RequestHandler(url_cum);
            String response = req.getResponse();

            try {
                JSONObject jsonObject = new JSONObject(response);
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

                    al_top.add(li);

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

            Log.d("Size",al_top.size()+"");
            ca_top = new CustomAdapter(TopRatedMovies.this,al_top);
            lv_top.setAdapter(ca_top);
        }
    }
}
