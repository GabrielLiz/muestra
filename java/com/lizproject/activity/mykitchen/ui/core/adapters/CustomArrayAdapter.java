package com.lizproject.activity.mykitchen.ui.core.adapters;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.model.Sqldatabase;
import com.lizproject.activity.mykitchen.ui.core.core.ExpandingLayout;
import com.lizproject.activity.mykitchen.ui.core.core.TxtCustom;
import com.lizproject.activity.mykitchen.ui.core.presenter.ExpandableListItem;
import com.lizproject.activity.mykitchen.ui.core.presenter.IngredientsImgManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;


/**
 * This is a custom array adapter used to populate the listview whose items will
 * expand to display extra content in addition to the default display.
 */
public class CustomArrayAdapter extends ArrayAdapter<ExpandableListItem> {

    private List<ExpandableListItem> mDatas;
    private int mLayoutViewResourceId;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context ctx;
    private int mister;
   private int post=1;
    public CustomArrayAdapter(Context context, int layoutViewResourceId, List<ExpandableListItem> data) {
        super(context, layoutViewResourceId, data);
        mDatas = data;

        this.ctx = context;

        mLayoutViewResourceId = layoutViewResourceId;

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .showImageForEmptyUri(R.drawable.ic_launcher).cacheOnDisk(true).cacheInMemory(false)
                .build();
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ExpandableListItem object = mDatas.get(position);
        mister = position;
        final hold holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutViewResourceId, parent, false);
            holder = new hold();
            holder.Frm=(LinearLayout) convertView.findViewById(R.id.layout_ingredient_list_gab);
            int post=1;
            for (int i=0;i<object.getRes().length;i++) {
                holder.cust = new TxtCustom(ctx);
                holder.cust.setText(post + " - "+object.getList(i));
                holder.Frm.addView(holder.cust);
                post++;
            }
            convertView.setTag(holder);

        }else {
            holder = (hold) convertView.getTag();

        }


        LinearLayout linearLayout = (LinearLayout) (convertView.findViewById(R.id.item_linear_layout));



        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams  (AbsListView.LayoutParams.MATCH_PARENT, object.getCollapsedHeight());
        linearLayout.setLayoutParams(linearLayoutParams);

        ImageView imgView = (ImageView) convertView.findViewById(R.id.image_view);
        TextView titleView = (TextView) convertView.findViewById(R.id.title_view);
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.btn_test);
        TextView viewTime = (TextView) convertView.findViewById(R.id.time_prepar_cook_gab);
        ImageView img1 = (ImageView) convertView.findViewById(R.id.img1_gab);
        ImageView img2 = (ImageView) convertView.findViewById(R.id.img2_gab);
        ImageView img3 = (ImageView) convertView.findViewById(R.id.img3_gab);
        ImageView img4 = (ImageView) convertView.findViewById(R.id.img4_gab);
        TextView descriptxt = (TextView) convertView.findViewById(R.id.descript_text_gab);
        btn.setFocusable(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();
                db.delete(Sqldatabase.TABLE_RECEPYS, Sqldatabase.KEY_TITULO + " = '" + object.getTitle() + "'", null);

                mDatas.remove(position);
               notifyDataSetChanged();
            }
        });
        titleView.setText(object.getTitle());
        imgView.getLayoutParams().height = 150;
        imgView.getLayoutParams().width = 100;
        descriptxt.setText(object.getDescrip());
//        imgView.setImageBitmap(getCroppedBitmap(BitmapFactory.decodeFile(object.getImgResource())));
        imageLoader.displayImage(object.getImgResource(), imgView, options);

        viewTime.setText(object.getTime());
        convertView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT));


        ExpandingLayout expandingLayout = (ExpandingLayout) convertView.findViewById(R.id.expanding_layout);

        String array[] = object.getRes();


        for (int ar = 0; ar < array.length; ar++) {



            if (array.length>=1){

                if (ar == 0) {
                    imageLoader.displayImage("drawable://" + new IngredientsImgManager(array[ar]).getDrawable(), img1, options);
                } else if (ar == 1) {
                    imageLoader.displayImage("drawable://" + new IngredientsImgManager(array[ar]).getDrawable(), img2, options);
                } else if (ar == 2) {
                    imageLoader.displayImage("drawable://" + new IngredientsImgManager(array[ar]).getDrawable(), img3, options);
                } else if (ar == 3) {
                    imageLoader.displayImage("drawable://" + new IngredientsImgManager(array[ar]).getDrawable(), img4, options);
                }
            }
            post++;
        }

        expandingLayout.setExpandedHeight(object.getExpandedHeight());

        expandingLayout.setSizeChangedListener(object);

        if (!object.isExpanded()) {
            expandingLayout.setVisibility(View.GONE);
        } else {
            expandingLayout.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    /**
     * Crops a circle out of the thumbnail photo.
     */
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        int halfWidth = bitmap.getWidth() / 2;
        int halfHeight = bitmap.getHeight() / 2;

        canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    static class hold {
        LinearLayout Frm;
        TxtCustom cust;
    }

}