package com.mac.chris.todoapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.NotesAdapter;
import com.mac.chris.todoapp.R;

import java.util.ArrayList;

/**
 * Created by chris on 4/3/16.
 */
public class NotesFragment extends Fragment {

    EditText addText;

    ArrayList<Note> notes;
    RecyclerView recyclerView;
    NotesAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fab;

    OnFragmentInteractionListener activity;

    public Firebase ref;

    @Override
    public void onAttach(Activity activity) {
        this.activity = (OnFragmentInteractionListener) activity;

        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Use Firebase to populate the list.
        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://todoapp1982.firebaseio.com/todoItems");

        addText = (EditText) rootView.findViewById(R.id.add_note);

        notes = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int i) {
                Toast.makeText(getActivity(), "onClick " + i, Toast.LENGTH_SHORT).show();
                activity.passNote(notes.get(i), String.valueOf(i));
            }

            @Override
            public void onLongClick(View v, int i) {
                Toast.makeText(getActivity(), "onLongClick " + i, Toast.LENGTH_SHORT).show();
                ref.orderByChild("text")
                        .equalTo((String) notes.get(i).getName().toString())
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
            }
        }));

        adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str = (String) dataSnapshot.child("text").getValue();
                Note nt = new Note(str);
                notes.add(nt);
                int pos = notes.indexOf(nt);
                adapter.notifyItemInserted(pos);
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String str = (String) dataSnapshot.child("text").getValue();
                Note nt = new Note(str);
                int pos = notes.indexOf(nt);
                notes.remove(nt);
                adapter.notifyItemRemoved(pos);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });

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
                    ref.push().child("text").setValue(addText.getText().toString());
                    addText.setText("");
                    Snackbar.make(v, "Note Added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                return false;
            }
        });

        /*// Delete items when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                new Firebase("https://todoapp1982.firebaseio.com/todoItems")

            }
        });*/

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

    public interface OnFragmentInteractionListener {
        void passNote(Note note, String str);
    }

    public interface ClickListener {
        public void onClick(View v, int i);
        public void onLongClick(View v, int i);
    }

}
