package com.example.quicknotes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknotes.DbHelper;
import com.example.quicknotes.MainActivity;
import com.example.quicknotes.Notes;
import com.example.quicknotes.R;
import com.example.quicknotes.UpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    Activity activity;
    ArrayList<Notes> list;
    public NotesAdapter(Activity activity ,ArrayList<Notes> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_view, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvMessage.setText(list.get(position).getMessage());
        holder.checkBox.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateActivity.class);
                intent.putExtra("id", String.valueOf(list.get(position).getId()));
                intent.putExtra("title", String.valueOf(list.get(position).getTitle()));
                intent.putExtra("message", String.valueOf(list.get(position).getMessage()));
                v.getContext().startActivity(intent);
            }
        });
        
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.item_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DbHelper dbHelper = DbHelper.getInstance(v.getContext());
                        int id = item.getItemId();
                        if (id == R.id.itemOpt_archive){
                            dbHelper.insertArchiveNote(list.get(position).getId(), list.get(position).getTitle(), list.get(position).getMessage());

                            dbHelper.deleteNote(list.get(position).getId());
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();

                            Toast.makeText(v.getContext(), "Save to Archive", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dbHelper.insertDeleteNote(list.get(position).getId(), list.get(position).getTitle(), list.get(position).getMessage());

                            dbHelper.deleteNote(list.get(position).getId());
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();

                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = (ArrayList<Notes>) filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvMessage;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_viewTitle);
            tvMessage = itemView.findViewById(R.id.tv_viewMessage);
            checkBox = itemView.findViewById(R.id.ck_checkBox);
        }
    }
}
