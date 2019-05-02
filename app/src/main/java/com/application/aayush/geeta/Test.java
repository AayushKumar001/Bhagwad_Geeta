package com.application.aayush.geeta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by Aayush on 8/27/2018.
 */

public class Test extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // rvItems = (RecyclerView) findViewById(R.id.rvItems);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerAdapter adapter = new RecyclerAdapter(this);
      /*  rvItems.setAdapter(adapter);
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));*/
    }
}
