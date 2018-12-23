package com.example.neerajsewani.notesapparchitecture;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addNote = findViewById(R.id.fab_add_notes);

        //


        //  adding a onClickListener
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //  setting up the recycler view and setting the adapter to it
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getExtras().getString(AddNote.TITLE);
            String description = data.getExtras().getString(AddNote.DESC);
            int priority = data.getExtras().getInt(AddNote.PRIORITY);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
        }
    }
}
