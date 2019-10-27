package com.kostikum.timeoverseer.adapters;

import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.Project;

public class GeneralItem extends ListItem {

    private Process process;
    private Project project;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}
