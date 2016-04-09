package com.mac.chris.todoapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;


public class EditFragment extends Fragment {

    EditText noteText;
    Note note;
    int position;

    SaveFragmentItem activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (SaveFragmentItem) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);

        noteText = (EditText) rootView.findViewById(R.id.editNote);
        if (note != null) {
            noteText.setText(note.getName());
        }

        return rootView;
    }

    public void setNote (Note note, int i) {
        this.note = note;
        this.position = i;
    }

    public interface SaveFragmentItem {
        public void saveNote(Note note, int i);
    }
}