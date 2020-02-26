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
import com.kostikum.timeoverseer.ui.DateCallback;
import com.kostikum.timeoverseer.ui.ProjectCallback;
import com.kostikum.timeoverseer.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class ProcessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> consolidatedList = new ArrayList<>();
    private LayoutInflater inflater;
    private ProjectCallback projectCallback;
    private DateCallback dateCallback;

    public ProcessListAdapter(Context context, ProjectCallback projectCallback, DateCallback dateCallback) {
        this.inflater = LayoutInflater.from(context);
        this.projectCallback = projectCallback;
        this.dateCallback = dateCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ListItem.TYPE_DATE:
                View viewDate = inflater
                        .inflate(R.layout.recyclerview_date_item, parent, false);
                holder = new DateViewHolder(viewDate);
                break;
            case ListItem.TYPE_GENERAL:
                View viewProcess = inflater
                        .inflate(R.layout.recyclerview_process_item, parent, false);
                holder = new ProcessViewHolder(viewProcess);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ListItem.TYPE_DATE:
                final DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) holder;
                dateViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateCallback.onClick(dateItem.getDate());
                    }
                });
                SimpleDateFormat newFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                String text = newFormat.format(dateItem.getDate());
                dateViewHolder.dateTextView.setText(text);
                break;
            case ListItem.TYPE_GENERAL:
                GeneralItem generalItem = (GeneralItem) consolidatedList.get(position);
                ProcessViewHolder processViewHolder = (ProcessViewHolder) holder;
                processViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectCallback.onClick(new Project("тестовый", R.color.light_green));
                    }
                });
                processViewHolder.titleTextView.setText(generalItem.getProject().getName());
                processViewHolder.titleTextView
                        .setBackgroundResource(generalItem.getProject().getColor());
                processViewHolder.durationTextView
                        .setText(Utils.formatStopwatchTime((long) generalItem.getProcess().getDuration()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return consolidatedList == null ? 0 : consolidatedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    public void setProcessesList(List<ListItem> list) {
        consolidatedList = list;
        notifyDataSetChanged();
    }

    class ProcessViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        TextView titleTextView;
        TextView durationTextView;

        ProcessViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.rv_process_item_layout);
            titleTextView = itemView.findViewById(R.id.process_title_textview);
            durationTextView = itemView.findViewById(R.id.process_duration_textview);
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        TextView dateTextView;

        DateViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.rv_date_item_layout);
            dateTextView = itemView.findViewById(R.id.date_textview);
        }
    }
}
