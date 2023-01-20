package com.example.quicknotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknotes.ArchiveActivity;
import com.example.quicknotes.Notes;
import com.example.quicknotes.R;

import java.util.ArrayList;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ViewHolder> {

    ArchiveActivity archiveActivity;
    ArrayList<Notes> archiveList;
    public ArchiveAdapter(ArchiveActivity archiveActivity, ArrayList<Notes> archiveList) {
        this.archiveActivity = archiveActivity;
        this.archiveList = archiveList;
    }

    @NonNull
    @Override
    public ArchiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_view, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveAdapter.ViewHolder holder, int position) {

        holder.tvTitle.setText(archiveList.get(position).getTitle());
        holder.tvMessage.setText(archiveList.get(position).getMessage());

        if (!archiveActivity.isContexualModeEnabled){
            holder.checkBox.setVisibility(View.GONE);
        }
        else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return archiveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvMessage;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_viewTitle);
            tvMessage = itemView.findViewById(R.id.tv_viewMessage);
            checkBox = itemView.findViewById(R.id.ck_checkBox);

            itemView.setOnLongClickListener(archiveActivity);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            archiveActivity.makeSelection(v, getAdapterPosition());
        }
    }
}
