package com.swatiag1101.imdb.RequestResponse;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by Swati Agarwal on 03-08-2015.
 */
public class RequestHandlerForSearch {

    private String url;

    public RequestHandlerForSearch(String url) {
        this.url = url;
    }

    public String getResponse(){
        String response="";

        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpResponse httpResponse =httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }

        return response;

    }
}
