package com.kostikum.timeoverseer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.viewmodel.MainViewModel;

import java.util.Date;
import java.util.List;

public class DatePieFragment extends Fragment {

    private Date date;
    private MainViewModel viewModel;
    private DatePieView datePieView;

    public static DatePieFragment newInstance(Date date) {
        return new DatePieFragment(date);
    }

    public DatePieFragment(Date date) {
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_pie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        float values[] = {0.25f, 0.25f, 0.25f, 0.25f};

        datePieView = view.findViewById(R.id.date_pie_view);
        datePieView.setData(values);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);


        List<Process> list = viewModel.getProcessesByDate(date);

        float total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += Integer.parseInt(list.get(i).getDuration());
        }
        float[] value_degree = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            value_degree[i] = 360 * (Integer.parseInt(list.get(i).getDuration()) / total);
        }


        datePieView.setData(value_degree);
    }
}
