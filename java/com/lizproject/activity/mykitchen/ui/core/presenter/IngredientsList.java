package com.lizproject.activity.mykitchen.ui.core.presenter;

import android.content.Context;

import com.lizproject.activity.mykitchen.R;

public class IngredientsList {
    private Context ctx;

    public IngredientsList(Context ctx){
       this.ctx=ctx;


    }
    public String[] Ingredientes(){


        String Ingredients[]=ctx.getResources().getStringArray(R.array.ingredients_array);
        return Ingredients;
    }




}
