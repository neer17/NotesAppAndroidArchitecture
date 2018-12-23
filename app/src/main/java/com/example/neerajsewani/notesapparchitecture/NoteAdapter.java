package com.example.neerajsewani.notesapparchitecture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        Note currentNote = notes.get(position);

        //  setting the values in the TextViews
        noteHolder.priorityTv.setText(String.valueOf(currentNote.getPriority()));
        noteHolder.descriptionTv.setText(currentNote.getDescription());
        noteHolder.titleTv.setText(currentNote.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    //  would initialize the "notes"
    public void setNotes(List<Note> notes){
        //  initializing the "notes" declared above
        this.notes = notes;
        notifyDataSetChanged(); //  to notify the adapter about the data change
    }

    class NoteHolder extends ViewHolder {

        private TextView titleTv;
        private TextView descriptionTv;
        private TextView priorityTv;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.text_view_title);
            descriptionTv = itemView.findViewById(R.id.text_view_description);
            priorityTv = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
