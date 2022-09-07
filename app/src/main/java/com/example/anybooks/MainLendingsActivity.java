package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainLendingsActivity extends AppCompatActivity {

    ImageView backBtn, emptyImageDisplay;
    RecyclerView recyclerView;
    FloatingActionButton addLendingBtn;
    TextView emptyTextDisplay;

    DatabaseHelper conn;
    ArrayList<String> lending_numb, lending_id, lending_person_name, lending_person_contact, lending_id_book, lending_id_paper, lending_id_multimedia, lending_start_date, lending_end_date;
    int lending_numb_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lendings);

        getWindow().setStatusBarColor(getColor(R.color.section_lendings2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerBookView);
        addLendingBtn = findViewById(R.id.addLendingBtn);

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