package com.kostikum.timeoverseer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.entity.Process;

import java.util.List;

public class ProcessListAdapter extends RecyclerView.Adapter<ProcessListAdapter.ProcessViewHolder> {

    private List<Process> processes;
    private LayoutInflater inflater;

    public ProcessListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProcessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_process_item, parent, false);
        return new ProcessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessViewHolder holder, int position) {
        Process process = processes.get(position);
        holder.titleTextView.setText(process.getProject());
        holder.durationTextView.setText(process.getDuration());
    }

    @Override
    public int getItemCount() {
        return processes != null ? processes.size() : 0;
    }

    public void setProcessesList(List<Process> list) {
        processes = list;
        notifyDataSetChanged();
    }

    class ProcessViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView durationTextView;

        ProcessViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.process_title_textview);
            durationTextView = itemView.findViewById(R.id.process_duration_textview);
        }
    }
}
