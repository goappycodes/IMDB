package com.swatiag1101.imdb.ActivityPackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.swatiag1101.imdb.Adapter.CustomAdapterForCast;
import com.swatiag1101.imdb.ListForCast;
import com.swatiag1101.imdb.R;
import com.swatiag1101.imdb.RequestResponse.RequestHandler;
import com.swatiag1101.imdb.RequestResponse.RequestHandlerForDetails;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DetailsOfMovies extends ActionBarActivity {

    private static String url = "http://api.themoviedb.org/";
    private static String api_ver = "3/";
    private static String context_path = "movie/";
    private static String api_key = "8496be0b2149805afa458ab8ec27560c";
    private static String cast_url = "http://api.themoviedb.org/3/movie/";

    private String url1 = "";
    private String url2 = "";
    private String url4 = "";
    JSONObject jsonObject,jsonObject1,jsonObject2,jsonObject3;
    String genre="",keywords="",translated="";
    TextView title,popularity,shortTitle,budget,revenue,status,vote,desc,keyword1,genre1,country1,release,traslate1;
    ImageView poster,star;
    ListView cast_view;
    ArrayList<ListForCast> al=new ArrayList<ListForCast>();
    CustomAdapterForCast cc;
    private String url3 = "http://api.themoviedb.org/3/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_movies);

        Intent intent = getIntent();
        long id;
        id = intent.getLongExtra("id",0);
        url1 = "http://api.themoviedb.org/"+api_ver+context_path+id+"?api_key="+api_key;
        url2 = cast_url+id+"/credits?api_key="+api_key;
        url3 = url3 + id+"/keywords?api_key="+api_key;
        url4 = cast_url+id+"/translations?api_key="+api_key;

        title = (TextView) findViewById(R.id.title);
        popularity = (TextView) findViewById(R.id.popularity);
        release = (TextView) findViewById(R.id.release);
        shortTitle = (TextView) findViewById(R.id.shortTitle);
        budget = (TextView) findViewById(R.id.budget);
        revenue = (TextView) findViewById(R.id.revenue);
        status = (TextView) findViewById(R.id.status);
        vote = (TextView) findViewById(R.id.vote);
        desc = (TextView) findViewById(R.id.desc);
        keyword1 = (TextView) findViewById(R.id.keyword1);
        genre1 = (TextView) findViewById(R.id.genre1);
        country1 = (TextView) findViewById(R.id.country1);
        cast_view = (ListView) findViewById(R.id.cast_view);
        poster = (ImageView) findViewById(R.id.poster);
        traslate1 = (TextView) findViewById(R.id.translate1);

        star = (ImageView) findViewById(R.id.star);
        star.setImageResource(R.drawable.blue_star);

        AsyncTaskForDetails asyncTaskForDetails = new AsyncTaskForDetails();
        asyncTaskForDetails.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_of_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {

            String message = "Text I want to share.";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AsyncTaskForDetails extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            RequestHandlerForDetails requestHandlerForDetails = new RequestHandlerForDetails(url1);
            String response  = requestHandlerForDetails.getResponse();

            RequestHandlerForDetails requestHandlerForDetails1 = new RequestHandlerForDetails(url2);
            String response1 = requestHandlerForDetails1.getResponse();


            RequestHandlerForDetails requestHandlerForDetails2 = new RequestHandlerForDetails(url3);
            String response2 = requestHandlerForDetails2.getResponse();

            RequestHandlerForDetails requestHandlerForDetails3 = new RequestHandlerForDetails(url4);
            String response3 = requestHandlerForDetails3.getResponse();

            Log.d("response1",response1);
            try {
                jsonObject = new JSONObject(response);
                jsonObject1 = new JSONObject(response1);
                jsonObject2 = new JSONObject(response2);
                jsonObject3 = new JSONObject(response3);

                JSONArray jsonArray1 = jsonObject1.getJSONArray("cast");
                for(int i=0;i<jsonArray1.length();i++){
                    JSONObject j =jsonArray1.getJSONObject(i);

                    ListForCast lc = new ListForCast();
                    lc.setUrl(j.getString("profile_path"));
                    lc.setName(j.getString("name"));

                    al.add(lc);
                    Log.d("image",j.getString("profile_path"));
                }
                Log.d("Json1",""+jsonObject1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                budget.setText("Budget: "+jsonObject.getString("budget"));
                Log.d("budget",jsonObject.getString("budget"));
                revenue.setText("Revenue: "+jsonObject.getString("revenue"));
                status.setText("Status: "+jsonObject.getString("status"));
                title.setText(jsonObject.getString("title"));
                shortTitle.setText(jsonObject.getString("tagline"));
                release.setText(jsonObject.getString("release_date"));
                vote.setText("("+jsonObject.getString("vote_average")+"/10)"+"\n"+jsonObject.getString("vote_count")+"users");
                desc.setText(jsonObject.getString("overview"));

                JSONArray jsonArray = jsonObject.getJSONArray("genres");
                for(int i=0;i<jsonArray.length();i++){
                    genre = genre+jsonArray.getJSONObject(i).getString("name")+" | ";

                }
                genre= genre.substring(0,genre.length()-3);
                genre1.setText(genre);
                Log.d("genre", genre);
                GetXMLTask getXMLTask = new GetXMLTask();
                String URL ="http://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path");
                getXMLTask.execute(new String[]{URL});
                Log.d("size",al.size()+"");
                cc = new CustomAdapterForCast(DetailsOfMovies.this,al);
                cast_view.setAdapter(cc);

                JSONArray jsonArray2 = jsonObject2.getJSONArray("keywords");
                for(int i=0;i<jsonArray2.length();i++){
                    keywords = keywords+jsonArray2.getJSONObject(i).getString("name")+" | ";

                }
                keyword1.setText(keywords);

                JSONArray jsonArray3 = jsonObject3.getJSONArray("translations");
                for(int i=0;i<jsonArray3.length();i++){
                    translated = translated+jsonArray3.getJSONObject(i).getString("english_name")+" | ";

                }
                traslate1.setText(translated);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            poster.setImageBitmap(result);
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
