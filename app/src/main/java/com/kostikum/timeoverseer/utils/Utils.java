package com.kostikum.timeoverseer.utils;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.adapters.DateItem;
import com.kostikum.timeoverseer.adapters.GeneralItem;
import com.kostikum.timeoverseer.adapters.ListItem;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static List<ListItem> transformList(List<Process> inputList) {
        HashMap<LocalDate, List<Process>> groupedHashMap = new HashMap<>();
        List<ListItem> consolidatedList = new ArrayList<>();

        for (Process process : inputList) {
            LocalDate hashMapKey = process.getLocalDate();
            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(process);
            } else {
                List<Process> list = new ArrayList<>();
                list.add(process);
                groupedHashMap.put(hashMapKey, list);
            }
        }

        for (LocalDate localDate : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setLocalDate(localDate);
            consolidatedList.add(dateItem);

            for (Process process : groupedHashMap.get(localDate)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setProcess(process);
                consolidatedList.add(generalItem);
            }
        }
        return consolidatedList;
    }

    public static List<ListItem> transformListOfProcessesAndProjects(List<ProcessWithProject> inputList) {

        TreeMap<LocalDate, List<ProcessWithProject>> groupedTreeMap = new TreeMap<>(new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                return o2.compareTo(o1);
            }
        });
        List<ListItem> consolidatedList = new ArrayList<>();

        for (ProcessWithProject processWithProject : inputList) {
            LocalDate hashMapKey = processWithProject.process.getLocalDate();
            if (groupedTreeMap.containsKey(hashMapKey)) {
                groupedTreeMap.get(hashMapKey).add(processWithProject);
            } else {
                List<ProcessWithProject> list = new ArrayList<>();
                list.add(processWithProject);
                groupedTreeMap.put(hashMapKey, list);
            }
        }


        for (LocalDate localDate : groupedTreeMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setLocalDate(localDate);
            consolidatedList.add(dateItem);

            for (ProcessWithProject processWithProject : groupedTreeMap.get(localDate)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setProcess(processWithProject.process);
                generalItem.setProject(processWithProject.project);
                consolidatedList.add(generalItem);
            }
        }
        return consolidatedList;
    }

    public static int getColorId(int id) {
        switch (id) {
            case R.id.greenRadioButton:
                return R.color.green;
            case R.id.orangeRadioButton:
                return R.color.orange;
            case R.id.pinkRadioButton:
                return R.color.pink;
            case R.id.brownRadioButton:
                return R.color.brown;
            case R.id.oliveRadioButton:
                return R.color.olive;
            case R.id.indigoRadioButton:
                return R.color.indigo;
            case R.id.greyRadioButton:
                return R.color.grey;
            case R.id.purpleRadioButton:
                return R.color.purple;
            case R.id.redRadioButton:
                return R.color.red;
            case R.id.lightGreenRadioButton:
                return R.color.light_green;
            case R.id.yellowRadioButton:
                return R.color.yellow;
            case R.id.blueRadioButton:
                return R.color.blue;
                default: return -1;
        }
    }

    public static String formatStopwatchTime(Long input) {
        long hours = TimeUnit.MILLISECONDS.toHours(input);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(input));

        if (hours > 0L) {
            String format = "%02d:%02d:%02d";
            return String.format(Locale.ENGLISH, format, hours, minutes, seconds);
        } else {
            String format = "%02d:%02d";
            return String.format(Locale.ENGLISH, format, minutes, seconds);
        }
    }
}
