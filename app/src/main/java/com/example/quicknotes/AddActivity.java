package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {


    Toolbar noteToolBar;
    EditText etNotes, etMessage;

    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        // Add(Save) Button
        dbHelper = DbHelper.getInstance(this);

        etNotes = findViewById(R.id.title_add);
        etMessage = findViewById(R.id.message_add);

        noteToolBar = findViewById(R.id.noteToolBar);
        setSupportActionBar(noteToolBar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            saveNote();
            Toast.makeText(this, "saved Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = etNotes.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (message.equals("")) {
            Toast.makeText(AddActivity.this, "Please Enter the Notes", Toast.LENGTH_SHORT).show();
        }
        else{
//                    dbHelper.insertNote(new Notes(etTitleAdd.getText().toString().trim(), etMessageAdd.getText().toString().trim()));
            dbHelper.insertNote(title, message);
            Toast.makeText(AddActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}