package com.kostikum.timeoverseer.ui;

import androidx.lifecycle.Lifecycle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.adapters.ProcessListAdapter;
import com.kostikum.timeoverseer.adapters.ProjectListAdapter;
import com.kostikum.timeoverseer.adapters.ListItem;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.Project;
import com.kostikum.timeoverseer.viewmodel.MainViewModel;

import java.text.DateFormat;
import java.text.ParseException;
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
                timeKeeperLayout.setVisibility(View.GONE);
            }
        });

        FloatingActionButton startButton = view.findViewById(R.id.add_project_button);
        startButton.setOnClickListener(new View.OnClickListener() {
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
        mViewModel.getAllListItems().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                processAdapter.setProcessesList(listItems);
            }
        });
        mViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectAdapter.setProjectsList(projects);
            }
        });
    }

    private final ProjectCallback projectCallback = new ProjectCallback() {
        @Override
        public void onClick(Project project) {
            timeKeeperTitleTextView.setText(project.getName());
            timeKeeperTimeTextView.setText(" ТУТА ВРЕМЯ!! ");
            timeKeeperLayout.setBackgroundResource(project.getColor());
            timeKeeperLayout.setVisibility(View.VISIBLE);

            Date date = new Date();

            DateFormat dateFormat = DateFormat.getDateInstance();

            try {
                date = dateFormat.parse(dateFormat.format(date));
            } catch (ParseException e) {
            }
            mViewModel.insert(new Process(date, project.getId(), 500));
        }
    };

    private final DateCallback dateCallback = new DateCallback() {
        @Override
        public void onClick(Date date) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showDatePieFragment(date);
            }
        }
    };
}
