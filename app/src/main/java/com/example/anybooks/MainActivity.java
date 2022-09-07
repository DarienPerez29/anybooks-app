package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView backBtn;
    CardView books_btn, papers_btn, multimedia_btn, lendings_btn, users_btn, info_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);

        getWindow().setStatusBarColor(getColor(R.color.color_mid));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        books_btn = findViewById(R.id.books_btn);
        papers_btn = findViewById(R.id.papers_btn);
        multimedia_btn = findViewById(R.id.multimedia_btn);
        lendings_btn = findViewById(R.id.lendings_btn);
        users_btn = findViewById(R.id.users_btn);
        info_btn = findViewById(R.id.info_btn);

        // ==================================
        // FUNCIONES PARA ACCEDER A SECCIONES
        // ==================================

        // Sección de libros
        books_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainBooksActivity.class);
            startActivity(load);
        });

        // Sección de articulos
        papers_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainPapersActivity.class);
            startActivity(load);
        });

        // Sección de multimedia
        multimedia_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainMultimediaActivity.class);
            startActivity(load);
        });

        // Sección de préstamos
        lendings_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainLendingsActivity.class);
            startActivity(load);
        });

        // Sección de usuarios
        users_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainUsersActivity.class);
            startActivity(load);
        });

        // Sección de información
        info_btn.setOnClickListener(v -> {
            Intent load = new Intent(MainActivity.this, MainInfoActivity.class);
            startActivity(load);
        });

    }
}