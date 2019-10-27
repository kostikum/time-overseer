package com.kostikum.timeoverseer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kostikum.timeoverseer.db.dao.ProjectDao;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.dao.ProcessDao;
import com.kostikum.timeoverseer.db.ProcessRoomDatabase;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;
import com.kostikum.timeoverseer.db.entity.Project;

import java.util.Date;
import java.util.List;

public class ProcessRepository {

    private ProcessDao mProcessDao;
    private ProjectDao mProjectDao;

    private LiveData<List<Process>> mAllProcesses;
    private LiveData<List<ProcessWithProject>> mAllProcessesWithProjects;
    private LiveData<List<Project>> mAllProjects;

    public ProcessRepository(Application application) {
        ProcessRoomDatabase database = ProcessRoomDatabase.getDatabase(application);

        mProcessDao = database.processDao();
        mAllProcesses = mProcessDao.getAllProcesses();
        mAllProcessesWithProjects = mProcessDao.getProcessesWithProjects();

        mProjectDao = database.projectDao();
        mAllProjects = mProjectDao.getAllProjects();
    }

    public LiveData<List<Process>> getAllProcesses() {
        return mAllProcesses;
    }

    public LiveData<List<ProcessWithProject>> getAllProcessesWithProjects() {
        return mAllProcessesWithProjects;
    }

    public List<Process> getProcessesByDate(Date date) {
        return mProcessDao.getProcessesByDate(date);
    }

    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public void insert(Process process) {
        new InsertProcessAsyncTask(mProcessDao).execute(process);
    }

    public void insert(Project project) {
        new InsertProjectAsyncTask(mProjectDao).execute(project);
    }

    private static class InsertProcessAsyncTask extends AsyncTask<Process, Void, Void> {

        private ProcessDao mAsyncTaskDao;

        InsertProcessAsyncTask(ProcessDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Process... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class InsertProjectAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskDao;

        InsertProjectAsyncTask(ProjectDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
