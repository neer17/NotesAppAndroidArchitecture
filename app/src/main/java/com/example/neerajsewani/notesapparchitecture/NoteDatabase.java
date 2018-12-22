package com.example.neerajsewani.notesapparchitecture;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    //  "Room" would generate all the code for "noteDao"
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    //  Callback
    public static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        //  When the database is created this callback is called
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        //  initializing "noteDao"
        private PopulateAsyncTask(NoteDatabase noteDatabase) {
            noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Desc 1", 1));
            noteDao.insert(new Note("Title 2", "Desc 2", 2));
            noteDao.insert(new Note("Title 3", "Desc 3", 3));
            return null;
        }
    }
}
