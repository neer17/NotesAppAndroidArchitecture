package com.example.neerajsewani.notesapparchitecture;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    private NoteViewModel noteViewModel;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addNote = findViewById(R.id.fab_add_notes);

        //  adding a onClickListener
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
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

        //  enabling the deletion of the notes on swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.deleteNoteAt(viewHolder.getAdapterPosition()));
                if (toast != null) toast = null;
                toast = Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT);
                toast.show();
            }
        }).attachToRecyclerView(recyclerView);

        //  setting up the update functionality
        adapter.setOnClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                String title = note.getTitle();
                int priority = note.getPriority();
                String description = note.getDescription();
                int id = note.getId();

                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                intent.putExtra(AddEditNote.TITLE, title);
                intent.putExtra(AddEditNote.DESC, description);
                intent.putExtra(AddEditNote.PRIORITY, priority);
                intent.putExtra(AddEditNote.ID, id);

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getExtras().getString(AddEditNote.TITLE);
            String description = data.getExtras().getString(AddEditNote.DESC);
            int priority = data.getExtras().getInt(AddEditNote.PRIORITY);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getIntExtra(AddEditNote.ID, -1) == -1) {
                Toast.makeText(this, "Note couldn't be edited", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNote.TITLE);
            String desc = data.getStringExtra(AddEditNote.DESC);
            int priority = data.getIntExtra(AddEditNote.PRIORITY, -1);
            int id = data.getIntExtra(AddEditNote.ID, -1);
            Note note = new Note(title, desc, priority);
            note.setId(id); //  passing the id in the database

            //  updating the note
            noteViewModel.update(note);
        } else {
            Toast.makeText(this, "Note couldn't be saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                if (toast != null) toast = null;
                toast = Toast.makeText(this, "delete all notes", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
