package com.kostikum.timeoverseer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.entity.Project;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<Project> projects;
    private LayoutInflater inflater;

    public ProjectListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_project_item, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projects.get(position);
        holder.nameTextView.setText(project.getName());
        holder.colorTextView.setText(project.getColor());
    }

    @Override
    public int getItemCount() {
        return projects != null ? projects.size() : 0;
    }

    public void setProjectsList(List<Project> list) {
        projects = list;
        notifyDataSetChanged();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView colorTextView;

        ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.project_name_textview);
            colorTextView = itemView.findViewById(R.id.project_color_textview);
        }
    }
}
