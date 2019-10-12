package com.kostikum.timeoverseer.ui;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.kostikum.timeoverseer.entity.Process;
import com.kostikum.timeoverseer.ProcessRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ProcessRepository mRepository;

    private LiveData<List<Process>> mAllProcesses;

    LiveData<HashMap<String, List<Process>>> mGroupedHashMap;

    public MainViewModel(Application app) {
        super(app);
        mRepository = new ProcessRepository(app);
        mAllProcesses = mRepository.getAllProcesses();

        mGroupedHashMap = Transformations
                .map(mAllProcesses, new Function<List<Process>,  HashMap<String, List<Process>>>() {
                    @Override
                    public  HashMap<String, List<Process>> apply(List<Process> input) {
                        return groupDataIntoHashMap(input);
                    }
                });
    }

    public void insert(Process process) {
        mRepository.insert(process);
    }


    LiveData<List<Process>> getAllProcesses() {
        return mAllProcesses;
    }

    LiveData<List<Process>> getAllProcessesHashed() {
        return mAllProcesses;
    }

    private HashMap<String, List<Process>> groupDataIntoHashMap(List<Process> listOfProcesses) {

        HashMap<String, List<Process>> groupedHashMap = new HashMap<>();

        for (Process process : listOfProcesses) {

            String hashMapKey = process.getDuration();

            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapKey).add(process);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<Process> list = new ArrayList<>();
                list.add(process);
                groupedHashMap.put(hashMapKey, list);
            }
        }


        return groupedHashMap;
    }
}
