package com.example.jessi.realtimereddit_androidclient;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetPosts extends AsyncTask<Void, Void, RedditPosts> {

    public static final String REDDIT_URL = "https://www.reddit.com/r/politics/rising.json?sort=new";

    public AsyncResponse delegate = null;

    @Override
    protected RedditPosts doInBackground(Void... voids) {

        String response = null;
        RedditPosts redditPosts = new RedditPosts();

        try{
            URL url = new URL(REDDIT_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }

                response = builder.toString();
                redditPosts = new Gson().fromJson(response, RedditPosts.class);

            }
            else{
                Log.d("Error", "The code is " + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return redditPosts;
    }

    @Override
    protected void onPostExecute(RedditPosts redditPosts) {
        delegate.proccessFinish(redditPosts);
    }

    public interface AsyncResponse{
        void proccessFinish(RedditPosts redditPosts);
    }
}
