package com.appodeal.support.test.items;

import android.graphics.Bitmap;

public class ListViewItem {

    private String title;
    private String description;
    private String sign;
    private String buttonAction;
    private float rating;
    private Bitmap adIcon;
    private Bitmap adImage;

    public ListViewItem(String title, String description, String sign, String buttonAction,
                        float rating, Bitmap adIcon, Bitmap adImage) {
        this.title = title;
        this.description = description;
        this.sign = sign;
        this.buttonAction = buttonAction;
        this.rating = rating;
        this.adIcon = adIcon;
        this.adImage = adImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSign() {
        return sign;
    }

    public String getButtonAction() {
        return buttonAction;
    }

    public Bitmap getAdIcon() {
        return adIcon;
    }

    public Bitmap getAdImage() {
        return adImage;
    }

    public float getRating() {
        return rating;
    }

}
