package com.lizproject.activity.mykitchen;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {



    @Override
    public void onCreate (Bundle saveStateInstance){
        super.onCreate(saveStateInstance);

    }
    /*
    protected   SQLiteOpenHelper getbase(){
        SQLiteOpenHelper   databasegabint=new SQLiteOpenHelper(this, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_TABLE_RECETAS);
                db.execSQL(CREATE_TABLE_INGREDIENTS);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);

                // create new tables
                onCreate(db);
            }
        };
        return databasegabint;
    }*/


    @Override
    public void onPause(){
        super.onPause();
    }


    @Override
    public void onResume(){
        super.onResume();

    }

}
