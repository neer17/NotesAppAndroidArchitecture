package com.example.neerajsewani.notesapparchitecture;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NotesRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
    }

    void insert(Note note){
        repository.insert(note);
    }

    void update(Note note){
        repository.update(note);
    }

    void delete(Note note){
        repository.delete(note);
    }

    void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
