package com.kostikum.timeoverseer.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.entity.Process;

import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private BottomSheetBehavior behavior;
    private TextView text;

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
        behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        text = view.findViewById(R.id.message);

        Button startButton = view.findViewById(R.id.start_button);
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
        mViewModel.getAllProcesses().observe(this, new Observer<List<Process>>() {
            @Override
            public void onChanged(List<Process> processes) {

                text.setText("0");
                if (processes != null) {
                    text.setText(Integer.toString(processes.size()));
                }
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });
    }
}
