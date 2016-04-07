package com.mac.chris.todoapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;


public class EditFragment extends Fragment {

    EditText noteText;
    Note note;

    public void setNotes (Note note, String i) {
        this.note = note;
        Log.d("SN","setNotes "+note.getName()+" "+noteText);
        noteText.setText(note.getName());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);

        noteText = (EditText) rootView.findViewById(R.id.editNote);

        return rootView;
    }
}