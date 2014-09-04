package com.lizproject.activity.mykitchen.ui.core.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.ui.core.adapters.DialogGridAdapter;
import com.lizproject.activity.mykitchen.ui.core.fragments.NewRecepyFrac;
import com.lizproject.activity.mykitchen.ui.core.presenter.PresenterListGrid;

import java.util.ArrayList;

/*
La ventana de dialog en que carga la lista de ingredientes en modo Fragment donde se puede selecionar

 */

public class SelectIngredientsDialog extends android.support.v4.app.DialogFragment {

    private GridView gridvi;
    private Button btndismiis;
    private ArrayList<PresenterListGrid> lista;
    private SQLiteDatabase db;
    private DialogGridAdapter theadapter;

    public ArrayList<PresenterListGrid> getLista() {
        return lista;
    }

    public void setLista(ArrayList<PresenterListGrid> lista) {
        this.lista = lista;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        setLista(lista);

    }

    // El mimso metodo de todos los Fragments onCreateView

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = getActivity().getLayoutInflater();

        arraylista();

        View vi = inflater.inflate(R.layout.dialog_fragment_ingredients, null);

        btndismiis = (Button) vi.findViewById(R.id.searcheable_btn_dialog_gab);

        GridView gridvi = (GridView) vi.findViewById(R.id.gridview_dialog_gab);

        btndismiis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getActivity().getIntent();

                i.putExtra("key", true);
                getTargetFragment().onActivityResult(getTargetRequestCode(), NewRecepyFrac.DIALOG_FRAGMENT, i);
                dismiss();
            }
        });

        btndismiis.setFocusable(false);

        theadapter = new DialogGridAdapter(getActivity(), -1, lista);
        gridvi.setAdapter(theadapter);

        gridvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!theadapter.getItem(position).getSelected())
                {
                    theadapter.getItem(position).setSelected(true);
                }else{
                    theadapter.getItem(position).setSelected(false);
                }
                theadapter.notifyDataSetChanged();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vi);



        builder.setTitle(getActivity().getTitle());


        return builder.create();
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

        while (c.moveToNext()) {
            keyid[count] = c.getInt(0);

            status[count] = c.getInt(3);
            count = count + 1;

        }
        for (int d = 0; d < status.length; d++) {
            if (status[d] == 1) {
                PresenterListGrid s = new PresenterListGrid(keyid[d] - 1);
                lista.add(s);

            }
        }
        for (int y = 0; y < status.length; y++) {
            if (status[y] == 0) {
                PresenterListGrid s = new PresenterListGrid(keyid[y] - 1);
                lista.add(s);

            }
        }
    }


    public static SelectIngredientsDialog newInstance() {
        SelectIngredientsDialog f = new SelectIngredientsDialog();

        // Supply num input as an argument.

        return f;

    }


}
