package com.mac.chris.todoapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;

import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;

public class EditNoteDialogFragment extends DialogFragment {

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static EditNoteDialogFragment newInstance(Note note) {
        EditNoteDialogFragment editNoteDialogFragment = new EditNoteDialogFragment();

        Bundle bundle = editNoteDialogFragment.newInstanceHelper(note, R.layout.fragment_edit);
        editNoteDialogFragment.setArguments(bundle);

        return editNoteDialogFragment;
    }

    /*
     * Helper method that creates a basic bundle of all of the information needed to change
     * values in a note
     */
    protected static Bundle newInstanceHelper(Note note, int resource) {
        Bundle bundle = new Bundle();
        bundle.putInt("LAYOUT_RESOURCE", resource);
        return bundle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
