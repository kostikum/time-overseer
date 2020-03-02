package com.kostikum.timeoverseer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.entity.Project;
import com.kostikum.timeoverseer.ui.ProjectCallback;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<Project> projects;
    private LayoutInflater inflater;
    private ProjectCallback projectCallback;

    public ProjectListAdapter(Context context, ProjectCallback callback) {
        this.inflater = LayoutInflater.from(context);
        this.projectCallback = callback;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_project_item, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        final Project project = projects.get(position);
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectCallback.onClick(project);
            }
        });
        holder.nameTextView.setText(project.getName());
        holder.colorTextView.setBackgroundResource(project.getColor());
    }

    @Override
    public int getItemCount() {
        return projects != null ? projects.size() : 0;
    }

    public void setProjectsList(List<Project> list) {
        projects = list;
        notifyDataSetChanged();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        TextView nameTextView;
        TextView colorTextView;

        ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.rv_project_item_layout);
            nameTextView = itemView.findViewById(R.id.project_name_textview);
            colorTextView = itemView.findViewById(R.id.project_color_textview);
        }
    }
}
