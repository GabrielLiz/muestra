package com.lizproject.activity.mykitchen.ui.core.presenter;

import com.lizproject.activity.mykitchen.R;

public class IngredientsImgManager {

    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public IngredientsImgManager(String keyA){

           int key=Integer.parseInt(keyA);

        if (key==1){
            setDrawable(R.drawable.cebolla);

        }else if (key==2){
            setDrawable(R.drawable.patata);
        }else if (key==3){
            setDrawable(R.drawable.pimiento_rojo);

        }else if (key==4){
            setDrawable(R.drawable.lechuga);
        }else if (key==5){
            setDrawable(R.drawable.pepino);
        }else if (key==6){
            setDrawable(R.drawable.tomate);
        }else if (key==7){
            setDrawable(R.drawable.zanahoria);
        }
    }



}
