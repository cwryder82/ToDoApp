package com.mac.chris.todoapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mac.chris.todoapp.fragments.AddNoteDialogFragment;
import com.mac.chris.todoapp.fragments.EditFragment;
import com.mac.chris.todoapp.fragments.NotesFragment;

public class MainActivity extends AppCompatActivity
        implements NotesFragment.OnFragmentInteractionListener, EditFragment.SaveFragmentItem {

    EditFragment editFrag;
    NotesFragment notesFrag;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesFrag = new NotesFragment();
        editFrag = new EditFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container1, notesFrag);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create an instance of the AddList dialog fragment and show it
     */
    public void showAddNoteDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = new AddNoteDialogFragment();
        dialog.show(fragmentManager, "AddNoteDialogFragment");
    }

    @Override
    public void toEdit(Note note, int i) {
        if (note!=null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container1, editFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            editFrag.setNote(note, i);
        }
    }

    @Override
    public void saveNote(Note oldnote, Note newnote, int i) {
        notesFrag.saveFromEdit(oldnote, newnote, i);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
