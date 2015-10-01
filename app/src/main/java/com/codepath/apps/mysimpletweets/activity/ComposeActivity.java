package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.twitterclient.TwitterApplication;
import com.codepath.apps.mysimpletweets.twitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    private TwitterClient client;
    private EditText etTweetMsg;
    String tweetMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
        etTweetMsg = (EditText) findViewById(R.id.etTweetMsg);
        Button btComposeTweet = (Button)findViewById(R.id.btComposeTweet);
        btComposeTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweetMsg = etTweetMsg.getText().toString();
                client.submitTweet(tweetMsg, new JsonHttpResponseHandler() {
                    //SUCCESS

                    public void onSuccess(int statusCode, JSONObject json) {

                    Log.d("Debug on Success", json.toString());
                    }


                    //FAILURE
                    public void onFailure(int statusCode, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG PopulateTimeline", errorResponse.toString());
                    }
                });
                Intent timelineIntent = new Intent(ComposeActivity.this,TimeLineActivity.class);
                startActivity(timelineIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
