package com.kostikum.timeoverseer.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kostikum.timeoverseer.entity.Process;
import com.kostikum.timeoverseer.ProcessRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ProcessRepository mRepository;

    private LiveData<List<Process>> mAllProcesses;

    public MainViewModel(Application app) {
        super(app);
        mRepository = new ProcessRepository(app);
        mAllProcesses = mRepository.getAllProcesses();
    }

    LiveData<List<Process>> getAllProcesses() {
        return mAllProcesses;
    }

    public void insert(Process process) {
        mRepository.insert(process);
    }
}
