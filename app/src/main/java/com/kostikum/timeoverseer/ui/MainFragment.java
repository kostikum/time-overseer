package com.kostikum.timeoverseer.ui;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.adapters.ProcessListAdapter;
import com.kostikum.timeoverseer.adapters.ProjectListAdapter;
import com.kostikum.timeoverseer.entity.Process;
import com.kostikum.timeoverseer.entity.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private BottomSheetBehavior behavior;

    private ProcessListAdapter processAdapter;
    private ProjectListAdapter projectAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        behavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet));
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);








        RecyclerView processRecyclerView = view.findViewById(R.id.processes_recyclerview);
        processRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        processAdapter = new ProcessListAdapter(getContext());
        processRecyclerView.setAdapter(processAdapter);

        RecyclerView projectsRecyclerView = view.findViewById(R.id.projects_recyclerview);
        projectsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        projectAdapter = new ProjectListAdapter(getContext());
        projectsRecyclerView.setAdapter(projectAdapter);

        List<Project> projects = new ArrayList<>();
        projects.add(new Project("Проект 1", "Красный"));
        projects.add(new Project("Проект 2", "Синий"));
        projects.add(new Project("Проект 3", "Зелёный"));
        projects.add(new Project("Проект 4", "Белый"));

        projectAdapter.setProjectsList(projects);

        FloatingActionButton startButton = view.findViewById(R.id.add_project_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.insert(new Process("thinking", "1"));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        processAdapter.setProcessesList(mViewModel.getAllProcesses().getValue());
        mViewModel.getAllProcesses().observe(this, new Observer<List<Process>>() {
            @Override
            public void onChanged(List<Process> processes) {
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                processAdapter.setProcessesList(processes);
            }
        });
    }
}
