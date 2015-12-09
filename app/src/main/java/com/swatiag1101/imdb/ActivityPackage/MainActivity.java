package com.swatiag1101.imdb.ActivityPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView lv;
    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter<String> aa;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv= (ListView) findViewById(R.id.list_of_menu_items);

         al.add("Latest Movies");
         al.add("Upcoming Movies");
         al.add("Now Playing Movies");
         al.add("Popular Movies");
         al.add("Top Rated Movies");
         aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,al);

        lv.setAdapter(aa);

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
        if(id==R.id.search){
            Intent intent = new Intent(MainActivity.this,Search_Page.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(id==0){
            Intent i = new Intent(MainActivity.this,Latest_Movies.class);
            startActivity(i);
        }else if(id==1){
            Intent i = new Intent(MainActivity.this,UpcomingMovies.class);
            startActivity(i);
        }else if(id==2){
            Intent i = new Intent(MainActivity.this,Latest_Movies.class);
            startActivity(i);
        }else if(id==3){
            Intent i = new Intent(MainActivity.this,PopularMovies.class);
            startActivity(i);
        }else{
            Intent i = new Intent(MainActivity.this,TopRatedMovies.class);
            startActivity(i);
        }


    }


}
