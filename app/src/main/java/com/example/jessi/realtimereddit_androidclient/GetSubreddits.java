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

public class GetSubreddits extends AsyncTask<Void, Void, SubredditModel> {

    public static final String SUBREDDIT_URL = "https://www.reddit.com/reddits.json";

    public GetSubreddits.AsyncResponse delegate = null;

    @Override
    protected SubredditModel doInBackground(Void... voids) {

        String response = null;
        SubredditModel subreddits = new SubredditModel();

        try{
            URL url = new URL(SUBREDDIT_URL);

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
                subreddits = new Gson().fromJson(response, SubredditModel.class);

            }
            else{
                Log.d("Error", "The code is " + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subreddits;
    }

    @Override
    protected void onPostExecute(SubredditModel subreddits) {
        delegate.proccessFinish(subreddits);
    }

    public interface AsyncResponse{
        void proccessFinish(SubredditModel subreddits);
    }

}
