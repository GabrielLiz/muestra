package com.lizproject.activity.mykitchen.ui.core.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.model.Sqldatabase;
import com.lizproject.activity.mykitchen.ui.core.presenter.PresenterListGrid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter<PresenterListGrid> implements StickyGridHeadersSimpleAdapter{

    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    private Context ctx;
    private Dialog dialog;
    private int resid;

    List <PresenterListGrid>lis;

    public GridViewAdapter(Context context, int resource, List<PresenterListGrid>objects) {
        super(context, resource, objects);

        this.ctx=context;
        this.lis=objects;
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .build();
    }


    @Override
    public long getHeaderId(int position) {

        return getItem(position).getCategory_id();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;


        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();


            convertView = inflater.inflate(R.layout.header, parent, false);


            holder.textView = (TextView)convertView.findViewById(R.id.text_des_gab);
            convertView.setTag(holder);


        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.textView.setText(getItem(position).getCategory());


        return convertView;
    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder gridViewImageHolder;
//            check to see if we have a view
        if (convertView == null) {
//                no view - so create a new one

           LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            view = inflater.inflate(R.layout.item_grid_image, parent, false);
            gridViewImageHolder = new ViewHolder();

            gridViewImageHolder.frame = (RelativeLayout) view.findViewById(R.id.fondo_img_grid_gab);
            gridViewImageHolder.imageView = (ImageView) view.findViewById(R.id.image);
            gridViewImageHolder.imageView.setMaxHeight(80);
            gridViewImageHolder.imageView.setMaxWidth(80);
            gridViewImageHolder.btn2=(Button) view.findViewById(R.id.btn_select_dialog_gab_type);
            gridViewImageHolder.textView=(TextView) view.findViewById(R.id.text_ingredient_title_gab);
            gridViewImageHolder.box_Fav=(CheckBox)view.findViewById(R.id.check_fav_star_gab);
            gridViewImageHolder.box_Fav.setChecked(getItem(position).getSelected());

            view.setTag(gridViewImageHolder);


        } else {
            gridViewImageHolder =(ViewHolder)view.getTag();
        }

        gridViewImageHolder.box_Fav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                }
            }
        });

        gridViewImageHolder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {" Kilogramos ", " Mililitros ", " Unidades ", " Cicharitas", "Tazas"};
                // arraylist to keep the selected items

                final ArrayList<Integer> seletedItems = new ArrayList();

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Escoja el tipo  de medida");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel

                            }
                        });


                dialog = builder.create();//AlertDialog dialog; create like this outside onClick

                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        for (int ars = 0; ars < seletedItems.size(); ars++) {
                            SQLiteDatabase db = mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();
                            ContentValues newregistro = new ContentValues();
                            int total = position + 1;
                            newregistro.put("medidas", seletedItems.get(ars));
                            db.update(Sqldatabase.TABLE_INGREDIENTS, newregistro, "id " + "=" + total, null);

                        }
                    }
                });
            }
        });

        imageLoader.displayImage("drawable://" + getItem(position).getResId(), gridViewImageHolder.imageView, options);


        return view;
    }


    protected class ViewHolder {
        TextView textView;
        ImageView imageView;
        Button btn2;
        RelativeLayout frame;
        CheckBox box_Fav;
    }
    protected class HeaderViewHolder {
        public TextView textView;
    }
}

