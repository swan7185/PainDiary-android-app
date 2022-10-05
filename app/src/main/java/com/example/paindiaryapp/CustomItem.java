package com.example.paindiaryapp;

public class CustomItem {
    private String spinnerItemName;
    private int spinnerItemImage;

    public CustomItem(String spinnerItemName, int spinnerItemImage){
        this.spinnerItemImage = spinnerItemImage;
        this.spinnerItemName = spinnerItemName;
    }

    public int getSpinnerItemImage() {
        return spinnerItemImage;
    }
    public String getSpinnerItemName() {
        return spinnerItemName;
    }
}
