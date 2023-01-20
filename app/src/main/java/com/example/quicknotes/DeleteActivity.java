package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quicknotes.adapter.DeleteAdapter;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity implements View.OnLongClickListener {

    Toolbar deleteToolBar;
    LinearLayout deletedLayout;

    RecyclerView delRecyclerView;
    DeleteAdapter deleteAdapter;

    DbHelper dbHelper;
    ArrayList<Notes> deletedList;

    ArrayList<Notes> selectionList;
    int counter = 0;

    public boolean isContexualModeEnabled = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // -----> Tool Bar
        deleteToolBar = findViewById(R.id.tb_deleteToolBar);
        setSupportActionBar(deleteToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recycler Bin");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // -----> Delete Recycler List
        delRecyclerView = findViewById(R.id.rv_delRecyclerView);
        dbHelper = DbHelper.getInstance(DeleteActivity.this);
        deletedList = new ArrayList<>();

        displayDeletedData();

        deleteAdapter = new DeleteAdapter(DeleteActivity.this, deletedList);
        delRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        delRecyclerView.setAdapter(deleteAdapter);

        // Deleted Visibility
        deletedLayout = findViewById(R.id.deletedLayout);
        if (deletedList.size() == 0){
            deletedLayout.setVisibility(View.VISIBLE);
            delRecyclerView.setVisibility(View.GONE);
        }
        else {
            deletedLayout.setVisibility(View.GONE);
            delRecyclerView.setVisibility(View.VISIBLE);
        }

        // Selection for Manage
        selectionList = new ArrayList<>();


    }

    // Delete Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Deleted Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            if (isContexualModeEnabled){
                removeContexualMode();
            }
            else {
                onBackPressed();
            }
        }
        else if (id == R.id.delOpt_delItems){
            deleteSelectionList();
            removeContexualMode();
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.delOpt_resItems){
            deleteSelectionList();
            restoreSelectedList();
            removeContexualMode();
            Toast.makeText(this, "Restored", Toast.LENGTH_SHORT).show();
        }
        else {
            deletedAllNotes();
            Toast.makeText(this, "Deleted All", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Display Notes into Delete List
    private void displayDeletedData() {
        ArrayList<Notes> list = dbHelper.fetchDeleteNotes();
        for (int i=list.size()-1; i>=0; --i){
            deletedList.add(new Notes(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getMessage()));
        }
    }

    // Long Click on Item
    @Override
    public boolean onLongClick(View v) {
        isContexualModeEnabled = true;
        deleteToolBar.getMenu().clear();
        deleteToolBar.inflateMenu(R.menu.del_contexual_menu);
        deleteToolBar.setTitle("0 Item Selected");
        deleteAdapter.notifyDataSetChanged();
        return true;
    }

    // Select the Record
    public void makeSelection(View v, int adapterPosition) {
        if (((CheckBox)v).isChecked()){
            selectionList.add(deletedList.get(adapterPosition));
            counter++;
            updateCounter();
        }
        else {
            selectionList.remove(deletedList.get(adapterPosition));
            counter--;
            updateCounter();
        }
    }
    public void updateCounter(){
        deleteToolBar.setTitle(counter+" Item Selected");
    }

    // Delete ForEver Selected Data
    private void deleteSelectionList() {
        for (int i=0; i<selectionList.size(); i++){
            dbHelper.removeDeleteNote(selectionList.get(i).getId());
            deletedList.remove(selectionList.get(i));
            deleteAdapter.notifyItemRemoved(i);
            deleteAdapter.notifyDataSetChanged();
        }
    }

    // Delete All Data ForEver
    private void deletedAllNotes() {
        for (int i=0; i<deletedList.size(); i++){
            dbHelper.removeDeleteNote(String.valueOf(deletedList.get(i).getId()));
            deletedList.remove(deletedList.get(i));
            deleteAdapter.notifyItemRemoved(i);
            deleteAdapter.notifyDataSetChanged();
        }
    }

    // Restore Selected Data
    private void restoreSelectedList() {
        for (int i=0; i<selectionList.size(); i++){
            dbHelper.insertNote(selectionList.get(i).getTitle(), selectionList.get(i).getMessage());
            MainActivity.setDeleteNotifyData(i, selectionList.get(i));
            deleteAdapter.notifyDataSetChanged();
        }
    }

    private void removeContexualMode() {
        counter = 0;
        selectionList.clear();
        isContexualModeEnabled = false;
        deleteToolBar.getMenu().clear();
        deleteToolBar.setTitle("Recycler Bin");
        deleteToolBar.inflateMenu(R.menu.delete_menu);
        deleteAdapter.notifyDataSetChanged();
    }

}