package com.lizproject.activity.mykitchen.ui.core.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.ui.core.adapters.GridViewAdapter;
import com.lizproject.activity.mykitchen.ui.core.presenter.PresenterListGrid;

import java.util.ArrayList;

public class IngredientsFrac extends Fragment  {
    private GridView gridvi;
    private ArrayList<PresenterListGrid> lista;
    private  SQLiteDatabase db;
    private GridViewAdapter theadapter;

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.my_ingredients_fracment, container, false);
            arraylista();
        gridvi = (GridView) vi.findViewById(R.id.grid_gab);
        theadapter = new GridViewAdapter(getActivity(),-1,lista);

        gridvi.setAdapter(theadapter);




        gridvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Usar el sistema de Refresh que uso en el Listview para que cuando se haga click se cambie decolor
                // y se acomode entre los primeros


                if (!lista.get(position).getHas_vegetable()) {
                    lista.get(position).setHas_vegetable(true);
                } else {
                    lista.get(position).setHas_vegetable(false);
                }

                theadapter.notifyDataSetChanged();
            }
        });

        return vi;


    }


    public void arraylista() {
        lista = new ArrayList<PresenterListGrid>();

        db = mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();
        String query = "SELECT * FROM ingredientes";

        Cursor c = db.rawQuery(query, null);
        int count = 0;
        int status[] = new int[c.getCount()];
        String mad[] = new String[c.getCount()];
        int keyid[] = new int[c.getCount()];
        int category[]= new int [c.getCount()];
        while (c.moveToNext()) {
            keyid[count] = c.getInt(0);
            mad[count] = c.getString(1);
            status[count] = c.getInt(3);
            category[count]=c.getInt(4);
            count = count + 1;

        }
        for (int d = 0; d < status.length; d++) {

           if (category[d]==2) {
                   PresenterListGrid s = new PresenterListGrid(keyid[d] - 1);
                   s.setName(mad[d]);
                   s.setCategory_id(category[d]);
                   lista.add(s);

           }
        }

        for (int y = 0; y < status.length; y++) {

                if (category[y]==1){
                PresenterListGrid s = new PresenterListGrid(keyid[y] - 1);
                s.setName(mad[y]);
                s.setCategory_id(category[y]);


                    s.setSelected(true);


                lista.add(s);
                }
        }

        for (int r = 0; r < status.length; r++) {

            if (category[r]==0){
                PresenterListGrid s = new PresenterListGrid(keyid[r] - 1);
                s.setName(mad[r]);
                s.setCategory_id(category[r]);



                lista.add(s);
            }
        }
    }

    private class TareaAsynk extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            arraylista();
            return null;
        }
    }
}

