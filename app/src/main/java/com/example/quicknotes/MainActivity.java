package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quicknotes.adapter.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar mainToolBar;
    LinearLayout notesLayout;
    FloatingActionButton floatingButton;

    RecyclerView recyclerView;
    static NotesAdapter notesAdapter;

    DbHelper dbHelper;
    static ArrayList<Notes> notesList;

    boolean displayVisibility = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -----> Tool Bar
        mainToolBar = findViewById(R.id.tb_mainToolBar);
        mainToolBar.setTitle("Quick Notes");
        setSupportActionBar(mainToolBar);
        
        // -----> Floating Button
        floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // -----> Notes Recycler List
        recyclerView = findViewById(R.id.rv_mainRecyclerView);
        dbHelper = DbHelper.getInstance(MainActivity.this);
        notesList = new ArrayList<>();

//        displayData();

        notesAdapter = new NotesAdapter(this ,notesList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.notifyDataSetChanged();

        displayData();

    }

    // Main Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.opt_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search your notes");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opt_search){
            Toast.makeText(this, "search the Note", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.opt_bin){
            Intent delIntent = new Intent(MainActivity.this, DeleteActivity.class);
            startActivity(delIntent);
        }
        else if (id == R.id.opt_archive){
            Intent arcIntent = new Intent(MainActivity.this, ArchiveActivity.class);
            startActivity(arcIntent);
        }
        else {
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    // Display Notes
    public void displayData() {
        ArrayList<Notes> list = dbHelper.fetchNotes();
        for (int i=list.size()-1; i>=0; --i){
            notesList.add(new Notes(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getMessage()));
        }

        // Notes Layout Visibility
        notesLayout = findViewById(R.id.notesLayout);
        if (notesList.size() > 0){
            notesLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            notesAdapter.notifyDataSetChanged();
        }
        else {
            notesLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            notesAdapter.notifyDataSetChanged();
        }

    }

    // Filter for Search
    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notesList){
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNote.getMessage().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(singleNote);
            }
        }
        notesAdapter.filterList(filteredList);
    }

    // Notify Restore Data
    public static void setDeleteNotifyData(int i, Notes notes) {
        notesList.add(i, new Notes(notes.getTitle(), notes.getMessage()));
        notesAdapter.notifyItemChanged(i);
        notesAdapter.notifyDataSetChanged();
    }

}