package com.kostikum.timeoverseer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.services.TimerService;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            showMainFragment();
        }
        startService(new Intent(this, TimerService.class));
    }

    public void showMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit();
    }

    public void showCreateProjectFragment() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("project")
                .replace(R.id.container, CreateProjectFragment.newInstance())
                .commit();
    }

    public void showDatePieFragment(Date date) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("date_pie")
                .replace(R.id.container, DatePieFragment.newInstance(date))
                .commit();
    }
}
