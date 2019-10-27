package com.kostikum.timeoverseer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.entity.Project;
import com.kostikum.timeoverseer.viewmodel.MainViewModel;

public class CreateProjectFragment extends Fragment {

    private MainViewModel mViewModel;

    public static CreateProjectFragment newInstance() {
        return new CreateProjectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_project_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.creating_project_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ((EditText) view.findViewById(R.id.project_name_edittext))
                        .getText().toString();
                String color = ((EditText) view.findViewById(R.id.project_color_edittext))
                        .getText().toString();

                mViewModel.insert(new Project(name, color)); //new Project(name, color);

                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    ((MainActivity) getActivity()).showMainFragment();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
