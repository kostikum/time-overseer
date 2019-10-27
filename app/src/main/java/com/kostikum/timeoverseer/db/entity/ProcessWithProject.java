package com.kostikum.timeoverseer.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProcessWithProject {

    @Embedded
    public Process process;

    @Relation(parentColumn = "project_id", entityColumn = "id")
    public Project project;
}
