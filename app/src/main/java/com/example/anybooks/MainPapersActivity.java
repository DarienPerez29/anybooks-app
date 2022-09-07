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

import com.example.anybooks.adapters.CustomPapersAdapter;
import com.example.anybooks.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainPapersActivity extends AppCompatActivity {

    ImageView backBtn, emptyImageDisplay;
    RecyclerView recyclerView;
    FloatingActionButton addPaperBtn;
    TextView emptyTextDisplay;

    DatabaseHelper conn;
    ArrayList<String> paper_numb, paper_id, paper_title, paper_author, paper_year, paper_language;
    int paper_numb_counter = 0;

    CustomPapersAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_papers);

        getWindow().setStatusBarColor(getColor(R.color.section_papers2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerPaperView);
        addPaperBtn = findViewById(R.id.addPaperBtn);

        emptyImageDisplay = findViewById(R.id.emptyImageDisplay);
        emptyTextDisplay = findViewById(R.id.emptyTextDisplay);

        conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        fillUpdatedList();

        // Intent para activity de agregar nuevo libro
        addPaperBtn.setOnClickListener(v -> {
            Intent load = new Intent(MainPapersActivity.this, AddPapersActivity.class);
            startActivity(load);
        });
    }

    // Se guardan todos los registros de la base de datos en listas
    public void storeDataInArrays(){
        // Consulta para extraer los registros
        Cursor cursor = null;
        String query = "SELECT * FROM " + Utilities.TABLA_ARTICULO;
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
                paper_numb_counter++;
                paper_numb.add(paper_numb_counter + "");
                paper_id.add(cursor.getString(0));
                paper_title.add(cursor.getString(1));
                paper_author.add(cursor.getString(2));
                paper_year.add(cursor.getString(3));
                paper_language.add(cursor.getString(4));
            }
            paper_numb_counter = 0;
            cursor.close();
        }
    }

    // Se actualizan los registros de la lista
    public void fillUpdatedList() {
        // Inicializamos los arreglos
        paper_numb = new ArrayList<>();
        paper_id = new ArrayList<>();
        paper_title = new ArrayList<>();
        paper_author = new ArrayList<>();
        paper_year = new ArrayList<>();
        paper_language = new ArrayList<>();

        // Se extraen y capturan los datos
        storeDataInArrays();

        // Llamamos nuestro adapter para desplegar los datos extraidos y capturados
        customAdapter = new CustomPapersAdapter(MainPapersActivity.this, paper_numb, paper_id, paper_title, paper_author, paper_year, paper_language);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainPapersActivity.this));
    }

    // Cuando cargue la activity, se intentarán actualizar los registros de la lista
    @Override
    public void onResume(){
        super.onResume();
        fillUpdatedList();
    }
}