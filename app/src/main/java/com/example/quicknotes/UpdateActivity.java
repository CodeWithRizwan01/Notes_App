package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    ImageView imgCrossBtn;
    TextView tvBtnUpdate;

    EditText etTitleUpdate;
    EditText etMessageUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Cross Button
        imgCrossBtn = findViewById(R.id.iv_imgCrossBtn);
        imgCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Update(Save) Button
        etTitleUpdate = findViewById(R.id.et_titleUpdate);
        etMessageUpdate = findViewById(R.id.et_messageUpdate);
        tvBtnUpdate = findViewById(R.id.tv_btnUpdate);

        etTitleUpdate.setText(getIntent().getStringExtra("title"));
        etMessageUpdate.setText(getIntent().getStringExtra("message"));

        String id = getIntent().getStringExtra("id");

        Toast.makeText(this, "id:"+id, Toast.LENGTH_SHORT).show();

        tvBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = DbHelper.getInstance(UpdateActivity.this);
                dbHelper.updateNote(id, etTitleUpdate.getText().toString().trim(), etMessageUpdate.getText().toString().trim());

                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}