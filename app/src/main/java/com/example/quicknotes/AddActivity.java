package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    
    ImageView imgBtnCross;

    TextView tvBtnAdd;
    EditText etTitleAdd;
    EditText etMessageAdd;

    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Cross Button
        imgBtnCross = findViewById(R.id.iv_imgBtnCross);
        imgBtnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Add(Save) Button
        dbHelper = DbHelper.getInstance(this);

        tvBtnAdd = findViewById(R.id.tv_btnAdd);
        etTitleAdd = findViewById(R.id.et_titleAdd);
        etMessageAdd = findViewById(R.id.et_messageAdd);

        tvBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitleAdd.getText().toString().trim();
                String message = etMessageAdd.getText().toString().trim();

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
        });
    }
}