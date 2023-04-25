package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

public class Feed extends AppCompatActivity {

    RecyclerView feedRV;

    ArrayList<Feed_Data> feeds;
    FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedRV = findViewById(R.id.feedRV);

//        feedAdapter = new FeedAdapter(this,phn,bg,address,gender,amt_of_blood,date,time,msg);

        Bg_Task bg_task = new Bg_Task(Feed.this);

        feeds = bg_task.getfeedsList();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(feeds.size());

                feedAdapter = new FeedAdapter(Feed.this,feeds);
                feedRV.setAdapter(feedAdapter);
            }
        },1000);
        feedRV.setLayoutManager(new LinearLayoutManager(this));


    }
}
