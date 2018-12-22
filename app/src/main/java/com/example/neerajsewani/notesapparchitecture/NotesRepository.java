package com.example.neerajsewani.notesapparchitecture;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NotesRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NotesRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);

        //  generally we cant call noteDao as its an abstract method
        //  but since we are using "Room" therefore, "Room" has generated
        //  all the code in "NoteDao" class
        //  "NoteDao" is called on the instance of the database
        noteDao = database.noteDao();

        //  again, "Room" has generated all the code for this method as well
        allNotes = noteDao.getAllNotes();
    }

    /**
     * below methods cant be executed as database operations cant happen
     * on the main thread
     * @param note
     */
    void insert(Note note){
        new AsyncInsert(noteDao).execute(note);
    }

    void update(Note note){
        new AsyncInsert(noteDao).execute(note);
    }

    void delete(Note note){
        new AsyncInsert(noteDao).execute(note);

    }

    void deleteAllNotes(){
        new AsyncInsert(noteDao).execute();

    }

    //  Async class for "insert method"
    private static class AsyncInsert extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        private AsyncInsert(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    //  Async class for "insert method"
    private static class AsyncUpdate extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        private AsyncUpdate(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    //  Async class for "insert method"
    private static class AsyncDelete extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        private AsyncDelete(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    //  Async class for "insert method"
    private static class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;

        private AsyncDeleteAll(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
