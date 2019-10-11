package com.kostikum.timeoverseer.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kostikum.timeoverseer.entity.Process;

@Database(entities = {Process.class}, version = 1)
public abstract class ProcessRoomDatabase extends RoomDatabase {

    public abstract ProcessDao processDao();
    private static volatile ProcessRoomDatabase INSTANCE;

    public static ProcessRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProcessRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProcessRoomDatabase.class, "process_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private ProcessDao mDao;

        PopulateDbAsync(ProcessRoomDatabase db) {
            mDao = db.processDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insert(new Process("programming", "100"));
            mDao.insert(new Process("walking", "30"));
            mDao.insert(new Process("cooking", "10"));
            return null;
        }
    }
}
