package com.mac.chris.todoapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;
import com.mac.chris.todoapp.adapters.NotesAdapter;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    final static String TITLE = "My Notes";

    EditText addText;

    ArrayList<Note> notes;
    ArrayList<String> keys;
    Firebase mquery;

    RecyclerView recyclerView;
    NotesAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fab;

    OnFragmentInteractionListener activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (OnFragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().setTitle(TITLE);

        notes = new ArrayList<>();
        keys = new ArrayList<>();

        //Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.setAndroidContext(getActivity());
        mquery = new Firebase("https://todoapp1982.firebaseio.com/todoItems");

        addText = (EditText) rootView.findViewById(R.id.add_note);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new NotesAdapter(mquery, Note.class, notes, keys);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int i) {
                activity.toEdit(notes.get(i), i);
            }

            @Override
            public void onLongClick(View v, int i) {
                mquery.orderByChild("text")
                        .equalTo((String) notes.get(i).getName())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }

                            public void onCancelled(FirebaseError firebaseError) {
                            }
                        });

                Toast.makeText(getActivity(), "Note Deleted at " + i, Toast.LENGTH_SHORT).show();
            }
        }));

        //ItemTouchHelper ith = new ItemTouchHelper();

        // Add items via the Button and EditText at the bottom of the window.
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText.setVisibility(View.VISIBLE);
                addText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        addText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mquery.push().child("text").setValue(addText.getText().toString());
                    addText.setText("");
                    Snackbar.make(v, "Note Added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, ClickListener listener) {

            this.clickListener = listener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child!=null && clickListener!=null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public void saveFromEdit(Note oldnote, final Note newnote, final int i) {
        mquery.orderByChild("text")
                .equalTo((String) oldnote.getName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                            firstChild.child("text").getRef().setValue(newnote.getName());
                            adapter.notifyItemChanged(i);
                        }
                    }

                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        void toEdit(Note note, int position);
    }

    public interface ClickListener {
        void onClick(View v, int i);
        void onLongClick(View v, int i);
    }

}
