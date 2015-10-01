package com.codepath.apps.mysimpletweets.adapter;


import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1,tweets);
    }

    //Override & setup custom template

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.Get tweet from position
        Tweet tweet = getItem(position);

        //2.Find or inflate the template
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }

        //3.Find subviews to fill with data in the template
        ImageView ivProfileView = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvRelativeTime = (TextView)convertView.findViewById(R.id.tvRelativeTime);

        //4.Populate the data into subview
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvRelativeTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        ivProfileView.setImageResource(android.R.color.transparent);//clear out old image
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileView);

        //Return the view to be inserted into view
        return convertView;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            if (relativeDate.contains(" minutes ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" minutes ago")) + "m ago";
            } else if(relativeDate.contains(" minute ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" minute ago")) + "m ago";
            } else if(relativeDate.contains(" hour ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" hour ago")) + "h ago";
            } else if(relativeDate.contains(" hours ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" hours ago")) + "h ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
