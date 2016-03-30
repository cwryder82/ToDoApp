package com.mac.chris.todoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> mNotes;

    public NotesAdapter(ArrayList<Note> notes) {
        mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View noteView = inflater.inflate(R.layout.item_note, parent, false);

        // Return a new holder instance
        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        // Get the data model based on position
        Note note = mNotes.get(position);

        // Set item views based on the data model
        TextView textView = holder.tv;
        textView.setText(note.getName());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public NoteViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.noteText);
        }
    }
}
