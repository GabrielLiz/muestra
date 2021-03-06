package com.lizproject.activity.mykitchen.ui.core.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.ui.core.presenter.PresenterListGrid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class DialogGridAdapter extends ArrayAdapter<PresenterListGrid> {

    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    private List<PresenterListGrid> mData;
    private Context ctx;
    private String ar[]={"fir","mis","ger"};
    /*
       El adaptador del GridView es usado dos veces en la pagina principal de ingredientes y en
       el "DialogFragment"  para agregar mas Ingredientes
     */
    public DialogGridAdapter(Context context, int layoutViewResourceId, List<PresenterListGrid> data) {
        super(context, layoutViewResourceId, data);
        this.mData = data;
        this.ctx = context;

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .build();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder gridViewImageHolder;
//            check to see if we have a view
        if (convertView == null) {
//                no view - so create a new one

            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            view = inflater.inflate(R.layout.dialog_grid_items, parent, false);
            gridViewImageHolder = new ViewHolder();
            gridViewImageHolder.frame = (RelativeLayout) view.findViewById(R.id.fondo_img_grid_gab);
            gridViewImageHolder.barseek=(SeekBar) view.findViewById(R.id.seek_bar_gab);

            gridViewImageHolder.imageView = (ImageView) view.findViewById(R.id.image);
            gridViewImageHolder.imageView.setMaxHeight(80);
            gridViewImageHolder.imageView.setMaxWidth(80);

            view.setTag(gridViewImageHolder);

        } else {
//                we've got a view
            gridViewImageHolder = (ViewHolder) view.getTag();
        }

        if( getItem(position).getSelected() ){
            gridViewImageHolder.barseek.setEnabled(true);
            Log.i("gol","firu"+position);
        }else {  gridViewImageHolder.barseek.setEnabled(false);

        }

        imageLoader.displayImage("drawable://" + getItem(position).getResId(), gridViewImageHolder.imageView, options);


        return view;
    }


    static class ViewHolder {

        SeekBar barseek;
        ImageView imageView;
        Button btn;
        RelativeLayout frame;
    }

}
