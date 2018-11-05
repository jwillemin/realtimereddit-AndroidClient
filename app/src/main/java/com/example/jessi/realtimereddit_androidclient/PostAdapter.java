package com.example.jessi.realtimereddit_androidclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    Context context;
    PostAdapter.ViewHolder viewHolder = null;
    int resource = 0;

    public PostAdapter(Context context, int resource, ArrayList<Post> redditPosts) {
        super(context, resource, redditPosts);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        } else {
            view = convertView;
        }

        Post post = getItem(position);

        viewHolder = new PostAdapter.ViewHolder();
        viewHolder.title = view.findViewById(R.id.post_title);
        viewHolder.upvotes = view.findViewById(R.id.upvotes);
        viewHolder.comments = view.findViewById(R.id.comments);

        viewHolder.title.setText(post.getTitle());
        viewHolder.upvotes.setText(String.valueOf(post.getUpvotes()) + " Upvotes");
        viewHolder.comments.setText(String.valueOf(post.getCommentCount()) + " Comments");

        return view;
    }

    private class ViewHolder{
        TextView title;
        TextView upvotes;
        TextView comments;
    }

}
