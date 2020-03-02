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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
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

            Date date = new Date();
            Date date2 = new Date();
            date2.setDate(2);

            DateFormat dateFormat1 = DateFormat.getDateInstance();

            Date todayWithZeroTime = date;

            try {
                todayWithZeroTime = dateFormat1.parse(dateFormat1.format(date));
            } catch (ParseException e) {
            }

            try {
                todayWithZeroTime = dateFormat1.parse(dateFormat1.format(date2));
            } catch (ParseException e) {
                todayWithZeroTime = date;
            }

            Project pr1 = new Project("Программирование", R.color.blue);
            Project pr2 = new Project("Прогулка", R.color.green);
            Project pr3 = new Project("Вязание", R.color.red);
            Project pr4 = new Project("Whistle blowing", R.color.yellow);

            mProjectDao.insert(pr1);
            mProjectDao.insert(pr2);
            mProjectDao.insert(pr3);
            mProjectDao.insert(pr4);
            mProjectDao.insert(pr4);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            LocalDate experimental = new LocalDate(1999, 1, 1);

            mProcessDao.insert(new Process(experimental, 1,25));
            mProcessDao.insert(new Process(experimental, 2, 100));
            mProcessDao.insert(new Process(experimental, 4, 80));
            mProcessDao.insert(new Process(experimental, 4, 50));

            LocalDate additional = new LocalDate(1677, 12, 31);

            mProcessDao.insert(new Process(additional, 1,25));
            mProcessDao.insert(new Process(additional, 2, 100));

            LocalDate extra = new LocalDate(1887, 6, 12);

            mProcessDao.insert(new Process(extra, 1,25));
            mProcessDao.insert(new Process(new LocalDate(1500, 6, 12), 2, 100));
            mProcessDao.insert(new Process(new LocalDate(1500, 3, 12), 2, 100));
            mProcessDao.insert(new Process(new LocalDate(1500, 4, 12), 2, 100));




            return null;
        }
    }
}
