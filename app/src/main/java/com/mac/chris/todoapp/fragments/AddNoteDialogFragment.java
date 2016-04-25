package com.mac.chris.todoapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;

/**
 * Created by chris on 4/25/16.
 */
public class AddNoteDialogFragment extends DialogFragment {

    EditText mEditTextNoteName;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddNoteDialogFragment newInstance() {
        AddNoteDialogFragment addNoteDialogFragment = new AddNoteDialogFragment();
        Bundle bundle = new Bundle();
        addNoteDialogFragment.setArguments(bundle);
        return addNoteDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()/***, Set THEME Here ***/);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_note, null);
        mEditTextNoteName = (EditText) rootView.findViewById(R.id.edit_text_note_name);

        /**
         * Call addShoppingList() when user taps "Done" keyboard action
         */
        mEditTextNoteName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addShoppingList();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addShoppingList();
                    }
                });

        return builder.create();
    }

    /**
     * Add new active list
     */
    public void addShoppingList() {
        // Get the reference to the root node in Firebase
        Firebase ref = new Firebase("https://todoapp1982.firebaseio.com/todoItems");
        // Get the string that the user entered into the EditText and make an object with it
        // We'll use "Anonymous Owner" for the owner because we don't have user accounts yet
        String userEnteredName = mEditTextNoteName.getText().toString();
        Note note = new Note(userEnteredName);

        // This will create the node for you if it doesn't already exist.
        // Then using the setValue menu it will serialize the ShoppingList POJO
        ref.push().setValue(note);
    }
}
