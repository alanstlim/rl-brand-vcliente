package com.example.rlbrand.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareItem {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final String SELECTED_ITEM = "Selected Items";

    public ShareItem(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(SELECTED_ITEM, 0);
        editor = preferences.edit();
    }

    public void saveItem (String urlImage, int position){

        editor.putString("UId" + position, urlImage);
        editor.commit();

    }

    public String recoverItem(int position){
        return preferences.getString("UId" + position, "Padrão");
    }


    public void deleteItem (int position){

        editor.remove("UId" + position);
        editor.commit();

    }

    public void clearList (){
        editor.clear();
        editor.commit();

    }

}
