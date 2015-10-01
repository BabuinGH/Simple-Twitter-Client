package com.codepath.apps.mysimpletweets.models;


import org.json.JSONException;
import org.json.JSONObject;

public class User {
    //list of attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    //Deserialize the user from JSON array to User(java array)
    public static User fromJSON(JSONObject jsonObject){
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return user object(java object)
        return u;
    }
}
