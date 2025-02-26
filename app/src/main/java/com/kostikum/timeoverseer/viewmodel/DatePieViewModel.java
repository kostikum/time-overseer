package com.kostikum.timeoverseer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kostikum.timeoverseer.BasicApp;
import com.kostikum.timeoverseer.ProcessRepository;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatePieViewModel extends AndroidViewModel {
    private ProcessRepository mRepository;

    private LiveData<List<ProcessWithProject>> mAllProcWProjByDate;
    private LiveData<List<ProcessWithProject>> mAllProcWProj;

    private DatePieViewModel(Application app, final LocalDate localDate) {
        super(app);

        mRepository = ((BasicApp) app).getRepository();
        mAllProcWProj = mRepository.getAllProcessesWithProjects();
        mAllProcWProjByDate = mRepository.getAllProcessesWithProjectsByDate(localDate);
    }

    public LiveData<List<ProcessWithProject>> getProcessesAndProjectsByDate() {
        return mAllProcWProjByDate;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final LocalDate localDate;

        public Factory(Application application, LocalDate localDate) {
            this.application = application;
            this.localDate = localDate;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DatePieViewModel(application, localDate);
        }
    }


}
