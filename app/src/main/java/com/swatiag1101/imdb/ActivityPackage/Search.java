package com.swatiag1101.imdb.ActivityPackage;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.swatiag1101.imdb.Adapter.CustomAdapter;
import com.swatiag1101.imdb.Adapter.CustomAdapterForSearch;
import com.swatiag1101.imdb.ListSearch;
import com.swatiag1101.imdb.R;
import com.swatiag1101.imdb.RequestResponse.RequestHandlerForSearch;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends ActionBarActivity {

    ListView lv_genre,lv_keyword,lv_released,lv_translate,lv_title,lv_trailers,lv_casts,lv_crew;
    ArrayList<ListSearch> as = new ArrayList<ListSearch>();
    ArrayAdapter<ListSearch> aa;
    CustomAdapterForSearch cs;
    private static String url = "http://api.themoviedb.org/";
    private static String api_ver = "3";
    private static String context_path = "genre/list";
    private static String api_key = "8496be0b2149805afa458ab8ec27560c";
    private static String url_cum = url+api_ver+"/"+context_path+"?api_key="+api_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv_genre = (ListView) findViewById(R.id.genrelist);
        /*lv_keyword = (ListView) findViewById(R.id.keywordlist);
        lv_released = (ListView) findViewById(R.id.releasedCountrieslist);
        lv_translate = (ListView) findViewById(R.id.translatedlist);
        lv_title = (ListView) findViewById(R.id.alternateTitlelist);
        lv_trailers = (ListView) findViewById(R.id.trailerslist);
        lv_casts = (ListView) findViewById(R.id.castslist);
        lv_crew = (ListView) findViewById(R.id.crewslist);
*/
        AsyncTaskForSearch asyncTaskForSearch = new AsyncTaskForSearch();
        asyncTaskForSearch.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    class AsyncTaskForSearch extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            RequestHandlerForSearch requestHandlerForSearch = new RequestHandlerForSearch(url_cum);
            String response = requestHandlerForSearch.getResponse();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray genre = jsonObject.getJSONArray("genres");
                for(int i=0;i<genre.length();i++){
                    JSONObject j = genre.getJSONObject(i);

                    String id=j.getString("id");
                    String name = j.getString("name");

                    ListSearch ls = new ListSearch();

                    ls.setId(Integer.parseInt(id));
                    ls.setName(name);

                    Log.d("id", "" + id);
                    Log.d("name",""+name);

                    as.add(ls);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Size", "" + as.size());
            cs = new CustomAdapterForSearch(Search.this,as);
            lv_genre.setAdapter(cs);
            //aa = new ArrayAdapter<ListSearch>(Search.this,android.R.layout.simple_list_item_1,android.R.id.text1,as);
            //lv_genre.setAdapter(aa);

        }
    }
}
