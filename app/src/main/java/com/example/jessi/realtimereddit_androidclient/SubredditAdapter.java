package com.example.jessi.realtimereddit_androidclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubredditAdapter extends ArrayAdapter<Subreddit> {

    Context context;
    SubredditAdapter.ViewHolder viewHolder = null;
    int resource = 0;

    public SubredditAdapter(Context context, int resource, ArrayList<Subreddit> subreddits) {
        super(context, resource, subreddits);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.subreddit_list_item, null);
        } else {
            view = convertView;
        }

        Subreddit subreddit = getItem(position);

        viewHolder = new SubredditAdapter.ViewHolder();
        viewHolder.subredditName = view.findViewById(R.id.subreddit_name);

        viewHolder.subredditName.setText(subreddit.getDisplayName());
        return view;
    }

    private class ViewHolder{
        TextView subredditName;
    }

}
