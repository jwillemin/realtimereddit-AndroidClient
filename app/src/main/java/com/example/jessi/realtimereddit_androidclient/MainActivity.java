package com.example.jessi.realtimereddit_androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetPosts.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.stories_label);

        GetPosts getPosts = new GetPosts();
        getPosts.delegate = this;
        getPosts.execute();
    }

    @Override
    public void proccessFinish(RedditPosts redditPosts) {

        final ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < redditPosts.getData().getChildren().length; i++){
            posts.add(redditPosts.getData().getChildren()[i].getData());
        }

        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getUpvotes() - p1.getUpvotes();
            }
        });

        ListView postList = (ListView) findViewById(R.id.post_list);
        PostAdapter adapter = new PostAdapter(MainActivity.this, R.layout.list_item, posts);
        postList.setAdapter(adapter);
    };
}
