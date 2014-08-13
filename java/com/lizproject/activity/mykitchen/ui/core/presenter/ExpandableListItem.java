package com.lizproject.activity.mykitchen.ui.core.presenter;

import android.net.Uri;
import android.os.Environment;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.ui.core.core.OnSizeChangedListener;

import java.io.File;

public class ExpandableListItem implements OnSizeChangedListener {

    private String mTitle;
    private String mText;
    private boolean mIsExpanded;
    private int mImgResource;
    private int mCollapsedHeight;
    private int mExpandedHeight;
    private String descrip;

    private String list[];
    private String res[];
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getRes() {
        return res;
    }

    public void setRes(String[] res) {
        this.res = res;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public ExpandableListItem(String title, String decrio,String tiempo, int collapsedHeight, String text,String []lista,String ids[]) {

        mTitle = title;
        mCollapsedHeight = collapsedHeight;
        this.descrip=decrio;
        this.time=tiempo;
        mIsExpanded = false;
        mText = text;
        mExpandedHeight = -1;
        list=lista;
        res=ids;

    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    public boolean isExpanded() {
        return mIsExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        mIsExpanded = isExpanded;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImgResource() {
      File fis=  new File(Environment.getExternalStorageDirectory() + "/imagesrecepys/" + getTitle() + ".jpg");
        if (fis.exists( )){
            return Uri.decode(Uri.fromFile(fis).toString());
        }else {
            return "drawable://" + R.drawable.cebolla;
        }



    }

    public int getCollapsedHeight() {
        return mCollapsedHeight;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        mCollapsedHeight = collapsedHeight;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getExpandedHeight() {
        return mExpandedHeight;
    }

    public void setExpandedHeight(int expandedHeight) {
        mExpandedHeight = expandedHeight;
    }

    @Override
    public void onSizeChanged(int newHeight) {
        setExpandedHeight(newHeight);
    }
}
