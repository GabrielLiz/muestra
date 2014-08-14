package com.lizproject.activity.mykitchen.ui.core.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.model.Sqldatabase;
import com.lizproject.activity.mykitchen.ui.core.adapters.CustomArrayAdapter;
import com.lizproject.activity.mykitchen.ui.core.core.ExpandingListView;
import com.lizproject.activity.mykitchen.ui.core.presenter.ExpandableListItem;

import java.util.ArrayList;
import java.util.List;

public class CoucineFrac extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Runnable{

    //debug options
    private static final boolean DEBUG = true;
    private static final String TAG = "gabito";

    //private Button btn;
    public ExpandingListView mListView;
    private final int CELL_DEFAULT_HEIGHT = 200;
    private List<ExpandableListItem> mData;
    private CustomArrayAdapter adapter;

    //RECEPY CUANDO SE LE DA GUARDAR TIENE QUE DEVOLVER LA ACTIVIDAD Y QUE LA VUELVA A CARGAR
    private SQLiteDatabase db;
    private SwipeRefreshLayout FracRefreshLyt;
    private Thread hilo;

    static CoucineFrac init() {
        return new CoucineFrac();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hilo = new Thread(this);
        hilo.start();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.my_kitchen_fragment, container, false);


        FracRefreshLyt = (SwipeRefreshLayout) vi.findViewById(R.id.swipe_container);
        FracRefreshLyt.setColorScheme(R.color.android_darkblue,
                R.color.android_orange, R.color.android_purple,
                R.color.android_red);

        ///FracRefreshLyt.canChildScrollUp();


        FracRefreshLyt.setOnRefreshListener(this);

     //   btn = (Button) vi.findViewById(R.id.btn_intent);




        adapter = new CustomArrayAdapter(getActivity(), R.layout.list_view_item, this.mData);


        mListView = (ExpandingListView) vi.findViewById(R.id.main_list_view);
        mListView.setAdapter(adapter);


        return vi;

    }


    public void getDataSql() {

        // Instancia la clase.
        db = mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();

        this.mData = new ArrayList<ExpandableListItem>();


        String query = "SELECT * FROM " + Sqldatabase.TABLE_RECEPYS;


        Cursor c = db.rawQuery(query, null);

        String info[];

        String data[];

        int res[];


        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String cod = c.getString(1);
                String lisp = c.getString(2);
                String tiem = c.getString(3);
                String nom = c.getString(4);
                String desc = c.getString(5);

                if (lisp!=null) {
                    data = lisp.split("\\s+");
                    info = new String[data.length];
                    res = new int[data.length];

                    for (int s = 0; s < data.length; s++) {

                            String quer = "SELECT * FROM " + Sqldatabase.TABLE_INGREDIENTS + " WHERE " + Sqldatabase.KEY_ID + " = " + data[s];

                            Cursor d = db.rawQuery(quer, null);

                            while (d.moveToNext()) {

                                res[s] = d.getInt(0);
                                info[s] = d.getString(1);

                            }
                    }
                }else {
                     info = new String[0];
                     data=new String[0];
                }


                mData.add(new ExpandableListItem(cod, desc, tiem, CELL_DEFAULT_HEIGHT, nom, info, data));
            } while (c.moveToNext());
        }

    }


    @Override
    public void onRefresh() {
         getDataSql();
        adapter.clear();
        adapter.addAll(mData);
        adapter.notifyDataSetChanged();
        FracRefreshLyt.setRefreshing(false);



    }


    @Override
    public void onStart() {
        if (DEBUG)
            Log.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        if (DEBUG)
            Log.i(TAG, "onResume()");
        super.onResume();

    }

    @Override
    public void onPause() {
        if (DEBUG)
            Log.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        if (DEBUG)
            Log.i(TAG, "onStop()");
        super.onStop();
    }


    @Override
    public void run() {
        try {
            getDataSql();

        }catch (IllegalThreadStateException exp){
            exp.printStackTrace();
        }
    }
}
