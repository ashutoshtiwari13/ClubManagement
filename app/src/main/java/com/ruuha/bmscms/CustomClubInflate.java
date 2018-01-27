package com.ruuha.bmscms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ruuha on 9/23/2017.
 */

public class CustomClubInflate{

    private int id;
    private String name;
    private String c_manager;
    private String contact;
    private byte[] image;

    public CustomClubInflate(int id, String name, String c_manager,String contact, byte[] image) {
        this.id = id;
        this.name = name;
        this.c_manager = c_manager;
        this.contact = contact;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getC_manager() {
        return c_manager;
    }

    public void setC_manager(String c_manager) {
        this.c_manager = c_manager;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
