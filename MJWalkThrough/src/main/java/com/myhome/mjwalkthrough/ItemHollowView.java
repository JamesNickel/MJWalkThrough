package com.myhome.mjwalkthrough;

import android.view.View;

public class ItemHollowView {

    private View Target;

    private String Title;

    private String Description;


    public ItemHollowView(){

    }

    public ItemHollowView(View target,
                          String title,
                          String description) {
        this.Target = target;
        this.Title = title;
        this.Description = description;
    }

    public View getTarget(){return this.Target;}

    public String getTitle(){return this.Title;}

    public String getDescription(){return this.Description;}


    public void setTarget(View value){this.Target = value;}

    public void setTitle(String value){this.Title = value;}

    public void setDescription(String value){this.Description = value;}
}
