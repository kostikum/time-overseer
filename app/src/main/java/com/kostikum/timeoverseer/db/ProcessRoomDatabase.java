package com.kostikum.timeoverseer.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.converter.DateConverter;
import com.kostikum.timeoverseer.db.dao.ProcessDao;
import com.kostikum.timeoverseer.db.dao.ProjectDao;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.Project;

import org.joda.time.LocalDate;

@Database(entities = {Process.class, Project.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class ProcessRoomDatabase extends RoomDatabase {

    public abstract ProcessDao processDao();
    public abstract ProjectDao projectDao();

    private static volatile ProcessRoomDatabase INSTANCE;

    public static ProcessRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProcessRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProcessRoomDatabase.class, "database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private ProcessDao mProcessDao;
        private ProjectDao mProjectDao;

        PopulateDbAsync(ProcessRoomDatabase db) {
            mProcessDao = db.processDao();
            mProjectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Project pr1 = new Project("Test project", R.color.purple);
            mProjectDao.insert(pr1);

            LocalDate experimental = new LocalDate(1900, 1, 1);
            mProcessDao.insert(new Process(experimental, 1,1));

            return null;
        }
    }
}
