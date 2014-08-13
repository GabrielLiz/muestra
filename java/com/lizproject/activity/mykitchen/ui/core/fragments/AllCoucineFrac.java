package com.lizproject.activity.mykitchen.ui.core.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizproject.activity.mykitchen.R;

public class AllCoucineFrac extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  vi= inflater.inflate(R.layout.fragment_wold_recepy,container,false);
        return vi;

    }
}
