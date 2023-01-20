package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quicknotes.adapter.ArchiveAdapter;

import java.util.ArrayList;

public class ArchiveActivity extends AppCompatActivity implements View.OnLongClickListener {

    Toolbar archiveToolBar;
    LinearLayout archiveLayout;

    RecyclerView archiveRecyclerView;
    ArchiveAdapter archiveAdapter;

    DbHelper dbHelper;
    ArrayList<Notes> archiveList;

    ArrayList<Notes> archSelectionList;
    int counter = 0;

    public boolean isContexualModeEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        // -----> Tool Bar
        archiveToolBar = findViewById(R.id.tb_archiveToolBar);
        setSupportActionBar(archiveToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Archive");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // -----> Archive Recycler List
        archiveRecyclerView = findViewById(R.id.rv_archiveRecyclerView);
        dbHelper = DbHelper.getInstance(ArchiveActivity.this);
        archiveList = new ArrayList<>();

        displayArchiveData();

        archiveAdapter = new ArchiveAdapter(ArchiveActivity.this, archiveList);
        archiveRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        archiveRecyclerView.setAdapter(archiveAdapter);

        // Archive Layout Visibility
        archiveLayout = findViewById(R.id.archiveLayout);
        if (archiveList.size() == 0){
            archiveLayout.setVisibility(View.VISIBLE);
            archiveRecyclerView.setVisibility(View.GONE);
        }
        else {
            archiveLayout.setVisibility(View.GONE);
            archiveRecyclerView.setVisibility(View.VISIBLE);
        }

        // Selection for Manage
        archSelectionList = new ArrayList<>();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ar_opt_unArchive){
            deleteSelectionList();
            unArchiveSelectedList();
            removeContexualMode();
            Toast.makeText(this, "UnArchived Notes", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.ar_opt_delete){
            deleteSelectionList();
            removeContexualMode();
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            if (isContexualModeEnabled){
                removeContexualMode();
            }
            else {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Display Notes into Archive List
    private void displayArchiveData() {
        ArrayList<Notes> list = dbHelper.fetchArchiveNotes();
        for (int i=list.size()-1; i>=0; --i){
            archiveList.add(new Notes(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getMessage()));
        }
    }

    // Long Click on Item
    @Override
    public boolean onLongClick(View v) {
        isContexualModeEnabled = true;
        archiveToolBar.inflateMenu(R.menu.archive_contexual_menu);
        archiveToolBar.setTitle("0 Item Selected");
        archiveAdapter.notifyDataSetChanged();
        return true;
    }

    // Select the Record
    public void makeSelection(View v, int adapterPosition) {
        if (((CheckBox)v).isChecked()){
            archSelectionList.add(archiveList.get(adapterPosition));
            counter++;
            updateCounter();
        }
        else {
            archSelectionList.remove(archiveList.get(adapterPosition));
            counter--;
            updateCounter();
        }
    }
    public void updateCounter(){
        archiveToolBar.setTitle(counter+" Item Selected");
    }

    // Delete ForEver Selected Data
    private void deleteSelectionList() {
        for (int i=0; i<archSelectionList.size(); i++){
            dbHelper.removeArchiveNote(archSelectionList.get(i).getId());
            archiveList.remove(archSelectionList.get(i));
            archiveAdapter.notifyItemRemoved(i);
            archiveAdapter.notifyDataSetChanged();
        }
    }

    // Restore Selected Data
    private void unArchiveSelectedList() {
        for (int i=0; i<archSelectionList.size(); i++){
            dbHelper.insertNote(archSelectionList.get(i).getTitle(), archSelectionList.get(i).getMessage());
            MainActivity.setDeleteNotifyData(i, archSelectionList.get(i));
            archiveAdapter.notifyDataSetChanged();
        }
    }

    private void removeContexualMode() {
        counter = 0;
        archSelectionList.clear();
        isContexualModeEnabled = false;
        archiveToolBar.getMenu().clear();
        archiveToolBar.setTitle("Archive");
        archiveAdapter.notifyDataSetChanged();
    }
}