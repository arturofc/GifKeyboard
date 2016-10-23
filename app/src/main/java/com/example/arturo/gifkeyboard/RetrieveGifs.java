package com.example.arturo.gifkeyboard;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arturo on 10/22/16.
 */

public class RetrieveGifs extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... gif_url){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag=cute+funny+cat+kitten")
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();

            System.out.println(jsonData);
            return jsonData;
        } catch (Exception e){
            this.exception = e;
            return null;
        }
    }
}
