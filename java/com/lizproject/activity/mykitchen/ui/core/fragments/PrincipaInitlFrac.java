package com.lizproject.activity.mykitchen.ui.core.fragments;


import android.annotation.TargetApi;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.ui.core.adapters.PageViewPagerAdapter;
import com.lizproject.activity.mykitchen.ui.core.core.PagerSlidingTabStrip;

public class PrincipaInitlFrac extends Fragment {

    private ViewPager vipager;
    private PageViewPagerAdapter pagerAdapter;
    private  PagerSlidingTabStrip tabStrip;

    StateListDrawable drawables = new StateListDrawable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View vi = inflater.inflate(R.layout.fragment_start_my_cook, container, false);

        vipager = (ViewPager) vi.findViewById(R.id.selected_view_pager);

        tabStrip = (PagerSlidingTabStrip) vi.findViewById(R.id.tabs);

        tabStrip.setShouldExpand(true);

        pagerAdapter = new PageViewPagerAdapter(getChildFragmentManager());

        // se agregan los dos Fragments al PageViewPagerAdapter
        pagerAdapter.addFragment(CoucineFrac.init());
        pagerAdapter.addFragment(new IngredientsFrac());

        vipager.setAdapter(pagerAdapter);
        tabStrip.setDividerColorResource(R.color.black);
        tabStrip.setBackgroundColor(getResources().getColor(R.color.android_blue));
        tabStrip.setIndicatorColorResource(R.color.android_darkgreen);
        // TabStrip se le agrega el contexto vipager para que puueda sber en que tab estar y otras funcionas
        tabStrip.setViewPager(vipager);


        return vi;
    }
}