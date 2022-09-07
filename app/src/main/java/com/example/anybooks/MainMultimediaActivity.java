package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainMultimediaActivity extends AppCompatActivity {

    ImageView backBtn, emptyImageDisplay;
    RecyclerView recyclerView;
    FloatingActionButton addMultimediaBtn;
    TextView emptyTextDisplay;

    DatabaseHelper conn;
    ArrayList<String> multimedia_numb, multimedia_id, multimedia_title, multimedia_author, multimedia_year, multimedia_language, multimedia_format;
    int multimedia_numb_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_multimedia);

        getWindow().setStatusBarColor(getColor(R.color.section_multimedia2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerBookView);
        addMultimediaBtn = findViewById(R.id.addMultimediaBtn);

        emptyImageDisplay = findViewById(R.id.emptyImageDisplay);
        emptyTextDisplay = findViewById(R.id.emptyTextDisplay);

//        conn = new DatabaseHelper(this, "AnyBooks", null, 1);
//        fillUpdatedList();
//
//        // Intent para activity de agregar nuevo libro
//        addBookBtn.setOnClickListener(v -> {
//            Intent load = new Intent(MainBooksActivity.this, AddBooksActivity.class);
//            startActivity(load);
//        });
    }
}