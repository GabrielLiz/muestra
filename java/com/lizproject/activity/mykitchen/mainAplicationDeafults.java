package com.lizproject.activity.mykitchen;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.lizproject.activity.mykitchen.model.Sqldatabase;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class mainAplicationDeafults extends Application{

    private static Sqldatabase databaseg;

    @Override
    public void onCreate() {

        super.onCreate();
        configureDefaultImageLoader(getApplicationContext());
        databaseg=new Sqldatabase(this);

       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            //////////////////////////////////
             SQLiteDatabase db=databaseg.getWritableDatabase();
            String data[]=getResources().getStringArray(R.array.ingredients_array);
            ContentValues nuevoRegistro = new ContentValues();
            for (int i=0; i< data.length;i++) {
                nuevoRegistro.put("titulo", data[i]);
                nuevoRegistro.put(Sqldatabase.KEY_QUANTY,0);
                nuevoRegistro.put(Sqldatabase.KEY_STATUS,0);
                db.insert(Sqldatabase.TABLE_INGREDIENTS, null, nuevoRegistro);
            }

////////////////////////////////////////////////////////////
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        databaseg.close();
        databaseg=null;

    }
    public static void configureDefaultImageLoader(Context context) {

        // Initialize ImageLoader with configuration.

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }

    public static Sqldatabase getDatabaseHelper() {
        return databaseg;
    }
}
