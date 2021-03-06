package com.example.jessi.realtimereddit_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetPosts.AsyncResponse {

    public static final int SUBREDDIT_CHANGE_REQ = 1;

    SwipeRefreshLayout swipeRefreshLayout = null;
    String subreddit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.stories_label);

        fetchData();

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
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
        final ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < redditPosts.getPostData().getChildren().length; i++){
            posts.add(redditPosts.getPostData().getChildren()[i].getData());
        }

        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getUpvotes() - p1.getUpvotes();
            }
        });

        ListView postList = (ListView) findViewById(R.id.post_list);
        PostAdapter adapter = new PostAdapter(MainActivity.this, R.layout.post_list_item, posts);
        postList.setAdapter(adapter);

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
