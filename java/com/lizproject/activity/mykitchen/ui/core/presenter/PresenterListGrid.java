package com.lizproject.activity.mykitchen.ui.core.presenter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.model.Sqldatabase;

public class PresenterListGrid {
    // aqui llena la informacion del GridView para la lista de ingredientes

    private int resId;
    private int quanty;
    private  boolean has_vegetable;
    private  int key_id;
    private  boolean selected;
    private String name;
    private boolean hasornot;
    private SQLiteDatabase db;
    private Context contxt;
    private  String query;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }




    public PresenterListGrid (int key_id){


        if (key_id==0){
            setResId(R.drawable.cebolla);

        }else if (key_id==1){
            setResId(R.drawable.patata);
        }else if (key_id==2){
            setResId(R.drawable.pimiento_rojo);

        }else if (key_id==3){
            setResId(R.drawable.lechuga);
        }else if (key_id==4){
            setResId(R.drawable.pepino);
        }else if (key_id==5){
            setResId(R.drawable.tomate);
        }else if (key_id==6){
            setResId(R.drawable.zanahoria);
        }
        setKey_id(key_id+1);
        db=  mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();
        query = "SELECT * FROM "+Sqldatabase.TABLE_INGREDIENTS +" WHERE "+Sqldatabase.KEY_ID +"="+getKey_id();
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()) {
                 setHas_vegetable(c.getInt(3) == 1);

        }
    }

    public boolean getHas_vegetable() {
        return has_vegetable;
    }


    public void setHas_vegetable(boolean has_vegetable) {
        this.has_vegetable = has_vegetable;
        ContentValues valores = new ContentValues();

        if (has_vegetable) {
            valores.put(Sqldatabase.KEY_STATUS, 1);
        }else {
            valores.put(Sqldatabase.KEY_STATUS, 0);
        }
        db.update(Sqldatabase.TABLE_INGREDIENTS,valores, Sqldatabase.KEY_ID+"= " + getKey_id(), null);

    }

    public int getKey_id() {

        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getQuanty() {
        return quanty;
    }

    public void setQuanty(int quanty) {
        this.quanty = quanty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;



    }
}