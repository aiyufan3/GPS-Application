package edu.msu.hegazyba.testproject;

import android.graphics.Bitmap;

public class BucketItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public BucketItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    private String name;
    private int image;
    private int id;
    private boolean completed = false;


}
