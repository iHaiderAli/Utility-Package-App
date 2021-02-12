package com.creative.utilitypackage.model;

import android.graphics.Bitmap;

public class ContactItem {

    private boolean isChecked = false;
    private String displayName;
    private String phone = "";
    private Bitmap photoUrl;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Bitmap getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Bitmap photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}