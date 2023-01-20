package com.example.quicknotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknotes.DeleteActivity;
import com.example.quicknotes.Notes;
import com.example.quicknotes.R;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> {

    DeleteActivity activity;
    ArrayList<Notes> deletedList;
    public DeleteAdapter(DeleteActivity  activity, ArrayList<Notes> deletedList) {
        this.activity = activity;
        this.deletedList = deletedList;
    }

    @NonNull
    @Override
    public DeleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_view, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.ViewHolder holder, int position) {

        holder.tvTitle.setText(deletedList.get(position).getTitle());
        holder.tvMessage.setText(deletedList.get(position).getMessage());

        if (!activity.isContexualModeEnabled){
            holder.checkBox.setVisibility(View.GONE);
        }
        else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return deletedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvMessage;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_viewTitle);
            tvMessage = itemView.findViewById(R.id.tv_viewMessage);
            checkBox = itemView.findViewById(R.id.ck_checkBox);

            itemView.setOnLongClickListener(activity);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            activity.makeSelection(v, getAdapterPosition());
        }
    }
}
