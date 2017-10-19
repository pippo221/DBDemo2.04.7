package com.example.cuongducnguyenkp.dbdemo2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Add New Tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab2"));

        // Set a event handler.
        TabListener tabListener = new TabListener(this);
        mTabLayout.setOnTabSelectedListener(tabListener);

    }
}