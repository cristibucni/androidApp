package com.exam.AndroidApp.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.exam.AndroidApp.loginsignup.R;



public class ImageTaker extends Fragment {

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.image_taker,container,false);
    }





}