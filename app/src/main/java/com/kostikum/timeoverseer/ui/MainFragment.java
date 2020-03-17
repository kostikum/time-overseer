package com.kostikum.timeoverseer.ui;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.adapters.ProcessListAdapter;
import com.kostikum.timeoverseer.adapters.ProjectListAdapter;
import com.kostikum.timeoverseer.adapters.ListItem;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;
import com.kostikum.timeoverseer.db.entity.Project;
import com.kostikum.timeoverseer.utils.Utils;
import com.kostikum.timeoverseer.viewmodel.MainViewModel;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private BottomSheetBehavior behavior;

    private ProcessListAdapter processAdapter;
    private ProjectListAdapter projectAdapter;

    private TextView timeKeeperTitleTextView;
    private TextView timeKeeperTimeTextView;
    private LinearLayout timeKeeperLayout;
    private CardView timekeeperCardView;

    private Boolean isRunning = false;
    private Handler updateHandler = new Handler();
    private long uptimeAtStart = 0L;
    private int totalTicks = 0;
    private int currentTicks = 0;
    private long UPDATE_INTERVAL = 1000L;
    private Process currentProcess;

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                updateDisplayedText();
                currentTicks++;
                updateHandler.postAtTime(this, uptimeAtStart + currentTicks * UPDATE_INTERVAL);
            }
        }
    };

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        behavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet));
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        timeKeeperLayout = view.findViewById(R.id.timekeeper_layout);
        timekeeperCardView = view.findViewById(R.id.cardview);
        timeKeeperTitleTextView = view.findViewById(R.id.timekeeper_title_textview);
        timeKeeperTimeTextView = view.findViewById(R.id.timekeeper_time_textview);

        RecyclerView processRecyclerView = view.findViewById(R.id.processes_recyclerview);
        processRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        processAdapter = new ProcessListAdapter(getContext(), projectCallback, dateCallback);
        processRecyclerView.setAdapter(processAdapter);

        RecyclerView projectsRecyclerView = view.findViewById(R.id.projects_recyclerview);
        projectsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        projectAdapter = new ProjectListAdapter(getContext(), projectCallback);
        projectsRecyclerView.setAdapter(projectAdapter);

        view.findViewById(R.id.timekeeper_stop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishProcess();
            }
        });
        view.findViewById(R.id.add_project_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    ((MainActivity) getActivity()).showCreateProjectFragment();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        processAdapter.setProcessesList(mViewModel.getAllListItems().getValue());
        mViewModel.getAllListItems().observe(getViewLifecycleOwner(), new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                processAdapter.setProcessesList(listItems);
            }
        });
        mViewModel.getAllProjects().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectAdapter.setProjectsList(projects);
            }
        });
    }

    private final ProjectCallback projectCallback = new ProjectCallback() {
        @Override
        public void onClick(Project project) {
            if (isRunning) {
                finishProcess();
            }

            LocalDate localDate = new LocalDate();

            List<ProcessWithProject> allPAP = mViewModel.getAllProcessWithProjects().getValue();
            currentProcess = null;
            if (allPAP != null) {
                for (ProcessWithProject pAP : allPAP) {
                    if (pAP.process.getLocalDate().equals(localDate) &&
                            pAP.project.getId() == project.getId()) {
                        currentProcess = pAP.process;
                        totalTicks = pAP.process.getDuration() / (int) UPDATE_INTERVAL;
                    }
                }

                if (currentProcess == null) {
                    currentProcess = new Process(localDate, project.getId(), 0);
                    totalTicks = 0;
                }
            }

            timeKeeperTitleTextView.setText(project.getName());
            int color = getActivity().getResources().getColor(project.getColor());
            timekeeperCardView.setCardBackgroundColor(color);
            timeKeeperLayout.setVisibility(View.VISIBLE);


            isRunning = true;
            timeKeeperLayout.setVisibility(View.VISIBLE);
            uptimeAtStart = SystemClock.uptimeMillis();
            updateHandler.post(updateRunnable);
        }
    };

    private void finishProcess() {
        isRunning = false;
        timeKeeperLayout.setVisibility(View.GONE);

        long session = (totalTicks + currentTicks) * UPDATE_INTERVAL;
        long totalDuration = SystemClock.uptimeMillis() - uptimeAtStart + session;
        updateHandler.removeCallbacksAndMessages(null);

        currentProcess.setDuration((int) session);
        mViewModel.insert(currentProcess);
        currentTicks = 0;
        totalTicks = 0;
    }

    private void updateDisplayedText() {
        timeKeeperTimeTextView
                .setText(Utils.formatStopwatchTime((totalTicks + currentTicks) * UPDATE_INTERVAL));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRunning && !getActivity().isChangingConfigurations()) {
            Toast.makeText(getContext(), "Tracking stopped", Toast.LENGTH_SHORT).show();
        }
        isRunning = false;
        updateHandler.removeCallbacks(updateRunnable);
    }

    private final DateCallback dateCallback = new DateCallback() {
        @Override
        public void onClick(LocalDate localDate) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showDatePieFragment(localDate);
            }
        }
    };
}
