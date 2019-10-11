package com.kostikum.timeoverseer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kostikum.timeoverseer.entity.Process;
import com.kostikum.timeoverseer.db.ProcessDao;
import com.kostikum.timeoverseer.db.ProcessRoomDatabase;

import java.util.List;

public class ProcessRepository {

    private ProcessDao mProcessDao;
    private LiveData<List<Process>> mAllProcesses;

    public ProcessRepository(Application application) {
        ProcessRoomDatabase database = ProcessRoomDatabase.getDatabase(application);
        mProcessDao = database.processDao();
        mAllProcesses = mProcessDao.getAllProcesses();
    }

    public LiveData<List<Process>> getAllProcesses() {
        return mAllProcesses;
    }

    public void insert(Process process) {
        new InsertAsyncTask(mProcessDao).execute(process);
    }

    private static class InsertAsyncTask extends AsyncTask<Process, Void, Void> {

        private ProcessDao mAsyncTaskDao;

        InsertAsyncTask(ProcessDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Process... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
