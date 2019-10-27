package com.kostikum.timeoverseer.utils;

import com.kostikum.timeoverseer.adapters.DateItem;
import com.kostikum.timeoverseer.adapters.GeneralItem;
import com.kostikum.timeoverseer.adapters.ListItem;
import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Utils {
    public static List<ListItem> transformList(List<Process> inputList) {

        HashMap<Date, List<Process>> groupedHashMap = new HashMap<>();
        List<ListItem> consolidatedList = new ArrayList<>();

        for (Process process : inputList) {
            Date hashMapKey = process.getDate();
            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(process);
            } else {
                List<Process> list = new ArrayList<>();
                list.add(process);
                groupedHashMap.put(hashMapKey, list);
            }
        }


        for (Date date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);

            for (Process process : groupedHashMap.get(date)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setProcess(process);
                consolidatedList.add(generalItem);
            }
        }
        return consolidatedList;
    }

    public static List<ListItem> transformListOfProcessesAndProjects(List<ProcessWithProject> inputList) {

        HashMap<Date, List<ProcessWithProject>> groupedHashMap = new HashMap<>();
        List<ListItem> consolidatedList = new ArrayList<>();

        for (ProcessWithProject processWithProject : inputList) {
            Date hashMapKey = processWithProject.process.getDate();
            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(processWithProject);
            } else {
                List<ProcessWithProject> list = new ArrayList<>();
                list.add(processWithProject);
                groupedHashMap.put(hashMapKey, list);
            }
        }


        for (Date date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);

            for (ProcessWithProject processWithProject : groupedHashMap.get(date)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setProcess(processWithProject.process);
                generalItem.setProject(processWithProject.project);
                consolidatedList.add(generalItem);
            }
        }
        return consolidatedList;
    }
}
