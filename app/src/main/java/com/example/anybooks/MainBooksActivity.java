package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anybooks.adapters.CustomBooksAdapter;
import com.example.anybooks.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainBooksActivity extends AppCompatActivity {

    ImageView backBtn, emptyImageDisplay;
    RecyclerView recyclerView;
    FloatingActionButton addBookBtn;
    TextView emptyTextDisplay;

    DatabaseHelper conn;
    ArrayList<String> book_numb, book_id, book_title, book_author, book_year, book_language, book_editorial, book_pages;
    int book_numb_counter = 0;

    CustomBooksAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_books);

        getWindow().setStatusBarColor(getColor(R.color.section_books2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerBookView);
        addBookBtn = findViewById(R.id.addBookBtn);

        emptyImageDisplay = findViewById(R.id.emptyImageDisplay);
        emptyTextDisplay = findViewById(R.id.emptyTextDisplay);

        conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        fillUpdatedList();

        // Intent para activity de agregar nuevo libro
        addBookBtn.setOnClickListener(v -> {
            Intent load = new Intent(MainBooksActivity.this, AddBooksActivity.class);
            startActivity(load);
        });
    }

    // Se guardan todos los registros de la base de datos en listas
    public void storeDataInArrays(){
        // Consulta para extraer los registros
        Cursor cursor = null;
        String query = "SELECT * FROM " + Utilities.TABLA_LIBRO;
        SQLiteDatabase db = conn.getReadableDatabase();
        if (db != null) cursor = db.rawQuery(query, null);

        // Se intentan guardar los datos recuperados en arreglos, si existen
        assert cursor != null;
        if (cursor.getCount() == 0) {
            emptyImageDisplay.setVisibility(View.VISIBLE);
            emptyTextDisplay.setVisibility(View.VISIBLE);
        } else {
            emptyImageDisplay.setVisibility(View.INVISIBLE);
            emptyTextDisplay.setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()) {
                book_numb_counter++;
                book_numb.add(book_numb_counter + "");
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_year.add(cursor.getString(3));
                book_language.add(cursor.getString(4));
                book_editorial.add(cursor.getString(5));
                book_pages.add(cursor.getString(6));
            }
            book_numb_counter = 0;
            cursor.close();
        }
    }

    // Se actualizan los registros de la lista
    public void fillUpdatedList() {
        // Inicializamos los arreglos
        book_numb = new ArrayList<>();
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_year = new ArrayList<>();
        book_language = new ArrayList<>();
        book_editorial = new ArrayList<>();
        book_pages = new ArrayList<>();

        // Se extraen y capturan los datos
        storeDataInArrays();

        // Llamamos nuestro adapter para desplegar los datos extraidos y capturados
        customAdapter = new CustomBooksAdapter(MainBooksActivity.this, book_numb, book_id, book_title, book_author, book_year, book_language, book_editorial, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainBooksActivity.this));
    }

    // Cuando cargue la activity, se intentarán actualizar los registros de la lista
    @Override
    public void onResume(){
        super.onResume();
        fillUpdatedList();
    }
}