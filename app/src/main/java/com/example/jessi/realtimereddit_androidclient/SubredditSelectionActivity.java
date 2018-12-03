package com.example.jessi.realtimereddit_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SubredditSelectionActivity extends AppCompatActivity implements GetSubreddits.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit_selection);
        setTitle("Top 25 Subreddits");

        fetchSubreddits();
    }

    public void fetchSubreddits(){
        GetSubreddits getSubreddits = new GetSubreddits();
        getSubreddits.delegate = this;
        getSubreddits.execute();
    }

    @Override
    public void proccessFinish(SubredditModel subredditModel) {

        final ArrayList<Subreddit> subreddits = new ArrayList<>();
        for (int i = 0; i < subredditModel.getSubredditData().getChildren().length; i++){
            subreddits.add(subredditModel.getSubredditData().getChildren()[i].getData());
        }

        ListView subredditList = (ListView) findViewById(R.id.subredditList);
        SubredditAdapter adapter = new SubredditAdapter(SubredditSelectionActivity.this, R.layout.subreddit_list_item, subreddits);
        subredditList.setAdapter(adapter);

        subredditList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("subreddit", subreddits.get(position).getDisplayName());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
