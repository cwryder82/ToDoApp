package com.mac.chris.todoapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mac.chris.todoapp.R;


public class EditFragment extends Fragment {

    EditText noteText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        noteText = (EditText) container.findViewById(R.id.editNote);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}