package com.swatiag1101.imdb.ActivityPackage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.swatiag1101.imdb.R;

import java.util.ArrayList;

public class Search_Page extends ActionBarActivity {

    ListView lv;
    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        lv = (ListView) findViewById(R.id.listview_for_search);
        al.add("Movies");
        al.add("Collection");
        al.add("Person");
        al.add("List");
        al.add("Company");

        aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,al);
        lv.setAdapter(aa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_page, menu);
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
}
