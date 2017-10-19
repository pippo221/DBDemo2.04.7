package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/18/2017.
 */

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//Our class extending fragment
public class Tab2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View retVal = inflater.inflate(R.layout.activity_tab_search2, container, false);
        return retVal;
    }
}