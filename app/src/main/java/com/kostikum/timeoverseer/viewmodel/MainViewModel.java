package com.kostikum.timeoverseer.viewmodel;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.kostikum.timeoverseer.BasicApp;
import com.kostikum.timeoverseer.adapters.DateItem;
import com.kostikum.timeoverseer.adapters.GeneralItem;
import com.kostikum.timeoverseer.adapters.ListItem;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.ProcessRepository;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;
import com.kostikum.timeoverseer.db.entity.Project;
import com.kostikum.timeoverseer.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ProcessRepository mRepository;

    private LiveData<List<Process>> mAllProcesses;
    private LiveData<List<ProcessWithProject>> mAllProcessWithProjects;
    private LiveData<List<Project>> mAllProjects;

    private LiveData<List<ListItem>> mConsolidatedList;
    private LiveData<List<ListItem>> mConsolidatedListOfProcessesAndProjects;

    public MainViewModel(Application app) {
        super(app);
        mRepository = ((BasicApp) app).getRepository();

        mAllProcesses = mRepository.getAllProcesses();
        mAllProcessWithProjects = mRepository.getAllProcessesWithProjects();
        mAllProjects = mRepository.getAllProjects();

        mConsolidatedList = Transformations
                .map(mAllProcesses, new Function<List<Process>,  List<ListItem>>() {
                    @Override
                    public  List<ListItem> apply(List<Process> input) {
                        return Utils.transformList(input);
                    }
                });

        mConsolidatedListOfProcessesAndProjects = Transformations
                .map(mAllProcessWithProjects, new Function<List<ProcessWithProject>,  List<ListItem>>() {
                    @Override
                    public  List<ListItem> apply(List<ProcessWithProject> input) {
                        return Utils.transformListOfProcessesAndProjects(input);
                    }
                });
    }

    public void insert(Process process) {
        mRepository.insert(process);
    }

    public void update(Process process) {
        mRepository.update(process);
    }

    public void insert(Project project) {
        mRepository.insert(project);
    }


    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public LiveData<List<ListItem>> getAllListItems() {
        return Transformations
                .map(mAllProcessWithProjects, new Function<List<ProcessWithProject>,  List<ListItem>>() {
                    @Override
                    public  List<ListItem> apply(List<ProcessWithProject> input) {
                        return Utils.transformListOfProcessesAndProjects(input);
                    }
                });
    }
}
