package com.mac.chris.todoapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(NoteViewHolder holder, int i) {

        holder.tv.setText(mNotes.get(i).getName().toString());

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView cv;
        public TextView tv;
        public ImageView img;

        public NoteViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            img = (ImageView) itemView.findViewById(R.id.noteImage);
            tv = (TextView) itemView.findViewById(R.id.noteText);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
