package com.mac.chris.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

/*
* Container Object to store notes
*/
public class Note {

    private String text;

    public Note(String text) {
            this.text = text;
    }

    public String getText() {
        return text;
    }

}
