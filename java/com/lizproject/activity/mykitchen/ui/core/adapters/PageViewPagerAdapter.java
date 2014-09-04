package com.lizproject.activity.mykitchen.ui.core.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.ui.core.core.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class PageViewPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    private List<Fragment> fragments;
    public  PageViewPagerAdapter(FragmentManager fm){
        super(fm);
        fragments=new ArrayList<Fragment>();
    }

    public void addFragment(Fragment frac){
        fragments.add(frac);

    }

    @Override
    public int getPageIconResId(int position) {
        int data=0;
            if (position==0){
                data=  R.drawable.ic_action_new;
            }else if (position==1){
                 data= R.drawable.ic_action_accept;
            }

        return data;

    }

    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

  /*  @Override
    public CharSequence getPageTitle(int position) {

        return TITLES[position];
    }*/

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
    }

}