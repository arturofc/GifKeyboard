package com.example.arturo.gifkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    Button search;
    EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.Search);
        display = (EditText)findViewById(R.id.editText);
        String gif_url = "http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag=cute+funny+cat+kitten";
        try{
            retrieveGifs(gif_url);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void retrieveGifs(String url) throws Exception{
        // Retrieve Gifs from Giphy
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    public void displayGifs(){

    }
}
