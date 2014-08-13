package com.lizproject.activity.mykitchen.ui.core.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lizproject.activity.mykitchen.R;
import com.lizproject.activity.mykitchen.RecepyAdd;
import com.lizproject.activity.mykitchen.mainAplicationDeafults;
import com.lizproject.activity.mykitchen.model.Sqldatabase;
import com.lizproject.activity.mykitchen.ui.core.dialog.SelectIngredientsDialog;
import com.lizproject.activity.mykitchen.ui.core.presenter.PresenterListGrid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;

public class NewRecepyFrac extends Fragment implements View.OnClickListener {

    private EditText editText_title_gab;
    private TextView textfulltitle;
    private Button btn_guardar, btn_consultar,btn_ingredients;
    private RadioGroup grouo_buttons_gab;
    private String Title;
    private String comida;
    private ImageView imgrecepy,img1,img2,img3,img4;
    private RadioButton radiobtn;
    private View vi;
    private EditText texdescription;
    private Button makerecepyimg;
    private SeekBar volumeControl=null;
    private TextView recepytitle;
    private String foto;
    private static final int TAKE_PICTURE = 1;
    private  double aleatorio = 0;
    public static final int DIALOG_FRAGMENT = 25;
    private  int progressChanged = 0;

    public ArrayList<PresenterListGrid>li;
    private Uri output;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    public static final int CAPTURE_IMAGE_THUMBNAIL_ACTIVITY_REQUEST_CODE = 1888;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

          vi=inflater.inflate(R.layout.frac_new_reccepy,container,false);

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher).cacheOnDisk(true).cacheInMemory(false)
                .build();

        imgrecepy=(ImageView)vi.findViewById(R.id.image_mi_receta_gab);
        editText_title_gab=(EditText)vi.findViewById(R.id.title_edit_text_gab);
        grouo_buttons_gab=(RadioGroup)vi.findViewById(R.id.buttoms_group_gab);
        btn_guardar=(Button)vi.findViewById(R.id.button_guardar_gab);
        btn_ingredients=(Button)vi.findViewById(R.id.add_ingredients_btn_gab);
        textfulltitle=(TextView)vi.findViewById(R.id.text_full_titulo_gab);
        recepytitle=(TextView)vi.findViewById(R.id.titulo_of_recepy_gab);
        makerecepyimg=(Button)vi.findViewById(R.id.camara_icon_gab  );
         img1=(ImageView)vi.findViewById(R.id.ingredient_select_1_gab);
        img2=(ImageView)vi.findViewById(R.id.ingredient_select_2_gab);
        img3=(ImageView)vi.findViewById(R.id.ingredient_select_3_gab);
        img4=(ImageView)vi.findViewById(R.id.ingredient_select_4_gab);
        volumeControl = (SeekBar)vi.findViewById(R.id.volume_bar);
        texdescription =(EditText)vi.findViewById(R.id.text_descrip_gab);
        setupUI(vi.findViewById(R.id.scroll_recepy_gab));
        /*
        editText_title_gab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    recepytitle.setText(editText_title_gab.getText().toString());
                    Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();

                }
            }
        });
*/
/*
        editText_title_gab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_title_gab.isFocused()){
                    if(editText_title_gab.length()>0){

                        recepytitle.setText(editText_title_gab.getText().toString());
                    }
                }
            }
        });
*/
              volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

         btn_ingredients.setOnClickListener(this);

        btn_guardar.setOnClickListener(this);

        makerecepyimg.setOnClickListener(this);
            return vi;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == DIALOG_FRAGMENT) {
                    boolean check = data.getBooleanExtra("key", true);
                    if(check)
                    {
                      li= dialog.getLista();
                        int d=0;
                        for(int i=0;i<li.size();i++){

                            if(li.get(i).getSelected()){
                                if (d==0) {
                                    imageLoader.displayImage("drawable://" + li.get(i).getResId(), img1, options);
                                }else if (d==1){
                                    imageLoader.displayImage("drawable://" + li.get(i).getResId(), img2, options);

                                }else if (d==2){
                                    imageLoader.displayImage("drawable://" + li.get(i).getResId(), img3, options);

                                }else if (d==3){
                                    imageLoader.displayImage("drawable://" + li.get(i).getResId(), img4, options);

                                }
                                d=d+1;

                            }


                        }
                    }
                    else
                    {
                    }
                }
                break;
            case TAKE_PICTURE:
                String decodedUri = Uri.decode(output.toString());
                imageLoader.displayImage(decodedUri,imgrecepy,options);

                break;
        }

    }


    @Override
    public void onPause() {
        super.onPause();
    }



    SelectIngredientsDialog dialog;
    @Override
    public void onClick(View v) {
        if (v==btn_guardar){


            //deberia ser null
            SQLiteDatabase  db= mainAplicationDeafults.getDatabaseHelper().getWritableDatabase();

            Title= editText_title_gab.getText().toString();

            int selected=grouo_buttons_gab.getCheckedRadioButtonId();

            radiobtn= (RadioButton)vi.findViewById(selected);
            comida=radiobtn.getText().toString();

            String descript =texdescription.getText().toString();
            ContentValues nuevoRegistro = new ContentValues();
            StringBuffer buffer=new StringBuffer();

            if (li!=null){

                for (int i =0;i< li.size(); i++){
                   if (li.get(i).getSelected()){

                 String sd= Integer.toString(li.get(i).getKey_id()  ) ;
                    buffer.append(sd+" ");
                   }
                }

                nuevoRegistro.put(Sqldatabase.KEY_LISTA, buffer.toString());
            }

            nuevoRegistro.put("titulo", Title);



            nuevoRegistro.put(Sqldatabase.KEY_TIEMPO,progressChanged+" min");
            nuevoRegistro.put("status", comida);
            nuevoRegistro.put(Sqldatabase.KEY_DESCRIPCION,descript);


            db.insert("recepys", null, nuevoRegistro);
            getActivity().finish();
        }else if (v==btn_ingredients){

             dialog = SelectIngredientsDialog.newInstance(1);
            dialog.setTargetFragment(this,DIALOG_FRAGMENT);
            dialog.show(getFragmentManager(), "dialog");
            if (dialog.getShowsDialog()){
                Log.i("gol","se esta viendo la imagen");

            }
        }
        else if (v==makerecepyimg){

            if(editText_title_gab.length()<=0){
                Toast.makeText(getActivity(), "Escoge un nombre de Receta", Toast.LENGTH_SHORT).show();
            }else{
                File f =new File( Environment.getExternalStorageDirectory()+"/imagesrecepys");
                if (!f.exists()){
                    f.mkdirs();
                }else {

                    foto = f.getAbsolutePath() + "/" + editText_title_gab.getText().toString() + ".jpg";
                    Log.i("gabo", foto);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                output = Uri.fromFile(new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, TAKE_PICTURE);}

        }
    }
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    RecepyAdd.hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

}
