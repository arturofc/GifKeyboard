package com.example.arturo.gifkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

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
        String gif_url = "http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag=cute+funny+cat+kitten";
        //new RetrieveGifs().execute(gif_url);

    }

    private GiphyGifData getGif(String jsonData) throws JSONException{
        JSONObject giphy = new JSONObject(jsonData);
        JSONObject data = giphy.getJSONObject("data");

        GiphyGifData gif = new GiphyGifData();
        gif.setUrl(data.getString("image_url"));

        return gif;

    }




}
