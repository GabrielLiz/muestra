package com.lizproject.activity.mykitchen.model;


import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Sqldatabase extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    private static final String DATABASE_NAME = "cocina.db";
    // Database Version
    private static final int DATABASE_VERSION = 12;
    // Common column users
    public static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    public static final String KEY_TITULO = "titulo";
    public static final String KEY_STATUS = "status";
    public static final String KEY_QUANTY = "medidas";
    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // Database Name

    // Table Recepys
    public static final String TABLE_RECEPYS = "recepys";
    public static final String TABLE_INGREDIENTS = "ingredientes";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";
    public static final String KEY_TIEMPO = "tiempo_preparacion";
    public static final String KEY_CATEGORY="Category";
    public static final String KEY_LISTA = "lista_ingredientes";
    public static final String KEY_DESCRIPCION = "descripcion";
    private static final String CREATE_TABLE_RECETAS = "CREATE TABLE "
            + TABLE_RECEPYS + "(" +
            /*0*/ KEY_ID + " INTEGER PRIMARY KEY, " +
            /*1*/ KEY_TITULO + " TEXT," +
            /*2*/ KEY_LISTA + " TEXT, " +
            /*3*/ KEY_TIEMPO + " TEXT," +
            /*4*/ KEY_STATUS + " TEXT, " +
            /*5*/ KEY_DESCRIPCION + " TEXT " + ")";

    private static final String CREATE_TABLE_INGREDIENTS = "CREATE TABLE "
            + TABLE_INGREDIENTS + "(" +
            /*1*/KEY_ID + " INTEGER PRIMARY KEY," +
            /*2*/KEY_TITULO + " TEXT," +
            /*3*/KEY_QUANTY + " INTEGER, " +
            /*4*/KEY_STATUS + " INTEGER, " +
            /*5*/KEY_CATEGORY+ " INTEGER " +
            ")";


    public Sqldatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_RECETAS);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEPYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);

        // create new tables
        onCreate(db);
    }
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
