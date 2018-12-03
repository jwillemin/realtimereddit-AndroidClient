package com.example.jessi.realtimereddit_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GetPosts.AsyncResponse {

    public static final int SUBREDDIT_CHANGE_REQ = 1;

    SwipeRefreshLayout swipeRefreshLayout = null;
    String subreddit = "";
    boolean realTimeRefresh = false;
    Timer t;
    PostAdapter adapter;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.stories_label);

        fetchData();
        setRealTimeRefresh();

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }

    public void setRealTimeRefresh(){
        if (realTimeRefresh){
            Log.i("realTimeRefresh: ", "ON");
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.i("realTimeRefresh: ", "Fetching...");
                    fetchData();
                }
            }, 0,5000);
        }
        else {
            Log.i("realTimeRefresh: ", "OFF");
            if (t != null){
                t.cancel();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.real_time_toggle:
                realTimeRefresh = !realTimeRefresh;
                setRealTimeRefresh();
                return true;

            case R.id.subreddit:
                Intent intent = new Intent(MainActivity.this, SubredditSelectionActivity.class);
                startActivityForResult(intent, SUBREDDIT_CHANGE_REQ);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void fetchData(){
        GetPosts getPosts = new GetPosts(subreddit);
        getPosts.delegate = this;
        getPosts.execute();
    }

    @Override
    public void proccessFinish(RedditPostModel redditPosts) {
        if (posts == null){
            posts = new ArrayList<>();
        } else posts.clear();
        for (int i = 0; i < redditPosts.getPostData().getChildren().length; i++){
            posts.add(redditPosts.getPostData().getChildren()[i].getData());
        }

        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getUpvotes() - p1.getUpvotes();
            }
        });

        ListView postList = findViewById(R.id.post_list);
        if (adapter == null){
            adapter = new PostAdapter(MainActivity.this, R.layout.post_list_item, posts);
            postList.setAdapter(adapter);
        }
        else adapter.notifyDataSetChanged();

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", posts.get(position).getUrl());
                startActivity(intent);
            }
        });

        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUBREDDIT_CHANGE_REQ) {
            if(resultCode == Activity.RESULT_OK){
                subreddit = data.getStringExtra("subreddit");
                fetchData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
