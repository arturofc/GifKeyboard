package com.example.arturo.gifkeyboard;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.util.LruCache;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Headers;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    Button Search;
    EditText display;

    private ImageView gifView;
    private GiphyGifData giphyGifData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText)findViewById(R.id.editText);
        gifView = (ImageView) findViewById(R.id.gifImage);
        Search = (Button) findViewById(R.id.Search);
        Search.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                String searchText = display.getText().toString(); //get the search Text entered by the user
                try{
                    retrieveGifs(searchText); //call the retrieve gif method with parameter: searchText
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"ERROR WHILE RETRIEVING GIFS",Toast.LENGTH_LONG).show();
                    System.out.println("Error while retrieving the gifs. Exception message: " + e.getMessage());
                }
            }
        });
        Toast.makeText(getApplicationContext(),"IT WORKS", Toast.LENGTH_LONG).show();

    }

    private GiphyGifData getGif(String jsonData) throws JSONException {
        GiphyGifData Gif = new GiphyGifData();
        String[] urls = new String[100];
        JSONObject giphy = new JSONObject(jsonData.trim());
        JSONObject data = giphy.getJSONObject("data");
        int i;
        for(i = 0; i < data.names().length(); i++) {
            urls[i] = data.getString("image_url");
        }
        Gif.setUrl(urls);
        return Gif;
    }

    public void retrieveGifs(String searchText) throws Exception{
        // Retrieve Gifs from Giphy
        OkHttpClient client = new OkHttpClient();

        String apiKey = "dc6zaTOxFJmzC"; //Giphy's Public API Key
        String url = "http://api.giphy.com/v1/gifs/random" +
                "?api_key=" +
                apiKey +
                "&tag=" +
                searchText;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

//                Headers responseHeaders = response.headers();
//                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }

                try{
                    String jsonData = response.body().string();
                    giphyGifData = getGif(jsonData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayGifs();
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayGifs(){


        String urls[] = giphyGifData.getUrl();
        int i = 0;
        Glide.with(MainActivity.this)
                .load(urls[i])
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(gifView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        //check isRefreshing
                    }
                });
    }
}
