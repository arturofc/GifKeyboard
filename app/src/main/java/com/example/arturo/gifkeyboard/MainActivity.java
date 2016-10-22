package com.example.arturo.gifkeyboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText)findViewById(R.id.testDisplay);
        try {
            getGiphy();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private GiphyGifData getGif(String jsonData) throws JSONException{
        JSONObject giphy = new JSONObject(jsonData);
        JSONObject data = giphy.getJSONObject("data");

        GiphyGifData gif = new GiphyGifData();
        gif.setUrl(data.getString("image_url"));

        return gif;

    }

    private void getGiphy() throws IOException{
        String giphyurl = "http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag=cute+funny+cat+kitten";

        if (isNetworkAvailable()) {


            HttpResponse response  = request.execute();
            display.setText(jsonData);
        }else{
            System.out.println("Network Error");
        }


    }


    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }



}
