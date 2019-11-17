package com.kostikum.timeoverseer.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.converter.DateConverter;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;
import com.kostikum.timeoverseer.viewmodel.DatePieViewModel;
import com.kostikum.timeoverseer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatePieFragment extends Fragment {
    private static final String KEY_DATE = "date_id";
    private LinearLayout linearLayout;
    private DatePieView datePieView;

    static DatePieFragment newInstance(Date date) {
        DatePieFragment fragment = new DatePieFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, DateConverter.toTimestamp(date));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_pie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = view.findViewById(R.id.under_the_pie_layout);
        datePieView = view.findViewById(R.id.date_pie_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatePieViewModel.Factory factory = new DatePieViewModel.Factory(
                requireActivity().getApplication(),
                DateConverter.toDate(getArguments().getLong(KEY_DATE)));

        DatePieViewModel viewModel = ViewModelProviders
                .of(getActivity(), factory).get(DatePieViewModel.class);
        final List<ProcessWithProject> list = viewModel.getProcessesAndProjectsByDate().getValue();
        setData(list);
        addProjectViews(list);
        viewModel.getProcessesAndProjectsByDate().observe(this,
                new Observer<List<ProcessWithProject>>() {
            @Override
            public void onChanged(List<ProcessWithProject> processes) {
                setData(processes);
                addProjectViews(processes);
            }
        });
    }

    private void setData(List<ProcessWithProject> list) {
        if (list != null && !list.isEmpty()) {
            List<Pair<Long, Integer>> pairs = new ArrayList<>();
            for (ProcessWithProject pr : list) {
                pairs.add(new Pair<>((long) pr.process.getDuration(), pr.project.getColor()));
            }
            datePieView.setData(pairs);
        }
    }

    private void addProjectViews(List<ProcessWithProject> list) {
        if (list != null && !list.isEmpty()) {
            for (ProcessWithProject pr : list) {
                TextView tv = new TextView(getActivity());
                tv.setText(pr.project.getName());
                tv.setBackgroundColor(getResources().getColor(pr.project.getColor()));
                tv.setPadding(2, 2, 2, 2);
                linearLayout.addView(tv);
            }
        }
    }
}
