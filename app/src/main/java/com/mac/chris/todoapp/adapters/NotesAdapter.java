package com.mac.chris.todoapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Query;
import com.mac.chris.todoapp.Note;
import com.mac.chris.todoapp.R;

import java.util.ArrayList;

public class NotesAdapter extends FirebaseRecyclerAdapter<NotesAdapter.NoteViewHolder, Note> {


    public NotesAdapter(Query query,
                        Class<Note> itemClass,
                        @Nullable ArrayList<Note> items,
                        @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.fragment_note_item, parent, false);

        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int i) {
        Note item = getItem(i);
        holder.textView.setText(item.getText());
        holder.timestampText.setText(String.valueOf(item.getTimestampLastChangedLong()));
    }

    @Override
    protected void itemAdded(Note item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter at index "+position);
    }

    @Override
    protected void itemChanged(Note oldItem, Note newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item at " + position);
    }

    @Override
    protected void itemRemoved(Note item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter at " + position);
    }

    @Override
    protected void itemMoved(Note item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView textView,timestampText;

        public NoteViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            textView = (TextView) itemView.findViewById(R.id.noteText);
            timestampText = (TextView) itemView.findViewById(R.id.timestampText);
        }
    }
}
