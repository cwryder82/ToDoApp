package com.mac.chris.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

/*
* Container Object to store notes
*/
public class Note implements Parcelable {

    private String text;

    public Note(String name) {
            text = name;
    }

    public Note(Parcel in) {
        this.text = in.readString();
    }

    public String getName() {
        return text;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[] {this.text});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
