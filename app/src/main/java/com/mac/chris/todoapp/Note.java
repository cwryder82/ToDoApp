package com.mac.chris.todoapp;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/*
* Container Object to store notes
*/
public class Note {

    private String text;
    private HashMap<String, Object> timestampLastChanged;

    public Note() {}

    public Note(String text) {
        this.text = text;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp",ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getText() {
        return text;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get("timestamp");
    }
}
