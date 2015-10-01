package com.codepath.apps.mysimpletweets.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Parse the JSON + store data, encap state logic or display logic
public class Tweet {
    //list out the attributes
    private String body;
    private long uid;  //unique id
    public User user; // Store embedded user data
    private String createdAt;

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    //Deserialize JSON & build Tweet objects
    //Tweet.fromJSON({...}) => <Tweet> {java object}
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Return the tweet object(java object)
        return tweet;
    }

    //Tweet.fromJSONArray({...},{...}) => list<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Iterate through JSONArray and create tweets
        for(int i=0; i<jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        //Return finished tweets
        return tweets;
    }
}
