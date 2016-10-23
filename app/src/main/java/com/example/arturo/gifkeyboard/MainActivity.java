package com.example.arturo.gifkeyboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button Search;
    EditText display;
    ListView listView;

    private GiphyGifData giphyGifData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText)findViewById(R.id.editText);
        listView = (ListView)findViewById(R.id.l1);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                copyUrl(position);
            }
        });

    }

    private GiphyGifData getGif(String jsonData) throws JSONException {
        GiphyGifData Gif = new GiphyGifData();

        JSONObject giphy = new JSONObject(jsonData);
        JSONArray data = giphy.getJSONArray("data");
        String[] urls = new String[data.length()];

        for(int i = 0; i < data.length(); i++){
            JSONObject image = (JSONObject)data.get(i);
            JSONObject type = image.getJSONObject("images").getJSONObject("downsized");
            urls[i] = type.getString("url");
        }
        Gif.setUrl(urls);
        return Gif;
    }

    public void retrieveGifs(String searchText) throws Exception{
        // Retrieve Gifs from Giphy
        OkHttpClient client = new OkHttpClient();

        String apiKey = "dc6zaTOxFJmzC"; //Giphy's Public API Key
        String url = "http://api.giphy.com/v1/gifs/search?" +
                "q=" + searchText +
                "&api_key=" +
                apiKey;

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

        listView.setAdapter(new ImageListAdapter(MainActivity.this, giphyGifData.getUrl()));

    }

    public void copyUrl (int position){
        String[] array  = giphyGifData.getUrl();
        String url = array[position];

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("GIF Image", url);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getApplicationContext(),"URL copied to clipboard",Toast.LENGTH_LONG).show();

    }
}
