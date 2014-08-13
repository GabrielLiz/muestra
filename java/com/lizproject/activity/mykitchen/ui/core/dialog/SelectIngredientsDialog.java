package com.lizproject.activity.mykitchen.ui.core.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.ui.core.adapters.GridViewAdapter;
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
    private int num;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.dialog_fragment_ingredients, container, false);

        //El arraylist() se encarga de consultar la base de datos y llenar la Lista para luego agregarcelo al adaptador

        arraylista();

        gridvi = (GridView) vi.findViewById(R.id.gridview_dialog);
        btndismiis = (Button) vi.findViewById(R.id.searcheable_btn_dialog_gab);

        btndismiis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getActivity().getIntent();

                i.putExtra("key", true);
                getTargetFragment().onActivityResult(getTargetRequestCode(), NewRecepyFrac.DIALOG_FRAGMENT, i);
                dismiss();
            }
        });


        final GridViewAdapter theadapter = new GridViewAdapter(getActivity(), -1, lista);
        gridvi.setAdapter(theadapter);

        /*
        gridvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Usar el sistema de Refresh que uso en el Listview para que cuando se haga click se cambie decolor
                // y se acomode entre los primeros


                if (!lista.get(position).getHas_vegetable()){
                    lista.get(position).setHas_vegetable(1);
                }else {
                    lista.get(position).setHas_vegetable(0);
                }

                theadapter.notifyDataSetChanged();
            }
        });*/

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = SelectIngredientsDialog.STYLE_NORMAL, theme = 0;

        switch ((num - 1) % 6) {
            case 1:
                style = SelectIngredientsDialog.STYLE_NO_TITLE;
                break;
            case 2:
                style = SelectIngredientsDialog.STYLE_NO_FRAME;
                break;
            case 3:
                style = SelectIngredientsDialog.STYLE_NO_INPUT;
                break;
            case 4:
                style = SelectIngredientsDialog.STYLE_NORMAL;
                break;
            case 5:
                style = SelectIngredientsDialog.STYLE_NORMAL;
                break;
            case 6:
                style = SelectIngredientsDialog.STYLE_NO_TITLE;
                break;
            case 7:
                style = SelectIngredientsDialog.STYLE_NO_FRAME;
                break;
            case 8:
                style = SelectIngredientsDialog.STYLE_NORMAL;
                break;
        }
        switch ((num - 1) % 6) {
            case 4:
                theme = android.R.style.Theme_Holo;
                break;
            case 5:
                theme = android.R.style.Theme_Holo_Light_Dialog;
                break;
            case 6:
                theme = android.R.style.Theme_Holo_Light;
                break;
            case 7:
                theme = android.R.style.Theme_Holo_Light_Panel;
                break;
            case 8:
                theme = android.R.style.Theme_Holo_Light;
                break;
        }
        setStyle(style, theme);

    }

    public static SelectIngredientsDialog newInstance(int num) {
        SelectIngredientsDialog f = new SelectIngredientsDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;

    }


}
