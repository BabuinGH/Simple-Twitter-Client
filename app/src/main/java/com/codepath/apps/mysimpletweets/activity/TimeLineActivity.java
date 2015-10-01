package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.twitterclient.TwitterApplication;
import com.codepath.apps.mysimpletweets.twitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimeLineActivity extends ActionBarActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter atweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        client = TwitterApplication.getRestClient();
        populateTimeLine();

        //find the list view
        lvTweets = (ListView)findViewById(R.id.lvTimeLine);
        // Attach the listener to the AdapterView onCreate
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
        //Create the arraylist of datasource
        tweets = new ArrayList<>();
        //Construct the adapter from data source
        atweets = new TweetsArrayAdapter(this,tweets);
        //Connect adapter to listview
        lvTweets.setAdapter(atweets);
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        populateTimeLine();
    }

    //Send API request to get the Timeline JSON
    //Fill the listview by creating the tweets objects from JSON
    private void populateTimeLine(){
        client.getHomeTimeLine(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                //Deserialize JSON
                //Create models & add them to adapter
                //load the model data into list view
                atweets.addAll(Tweet.fromJSONArray(json));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {

            Intent composeIntent = new Intent(this,ComposeActivity.class);
            startActivity(composeIntent);
            Toast.makeText(this,"Compose Tweet",Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
