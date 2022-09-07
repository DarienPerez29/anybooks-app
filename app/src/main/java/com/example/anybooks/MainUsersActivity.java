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

import com.example.anybooks.adapters.CustomUsersAdapter;
import com.example.anybooks.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainUsersActivity extends AppCompatActivity {

    ImageView backBtn, emptyImageDisplay;
    RecyclerView recyclerView;
    FloatingActionButton addUserBtn;
    TextView emptyTextDisplay;

    DatabaseHelper conn;
    ArrayList<String> user_numb, user_id, user_name;
    int user_numb_counter = 0;

    CustomUsersAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_users);

        getWindow().setStatusBarColor(getColor(R.color.section_users2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerUserView);
        addUserBtn = findViewById(R.id.addUserBtn);

        emptyImageDisplay = findViewById(R.id.emptyImageDisplay);
        emptyTextDisplay = findViewById(R.id.emptyTextDisplay);

        conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        fillUpdatedList();

        // Intent para activity de agregar nuevo usuario
        addUserBtn.setOnClickListener(v -> {
            Intent load = new Intent(MainUsersActivity.this, AddUsersActivity.class);
            startActivity(load);
        });
    }

    // Se guardan todos los registros de la base de datos en listas
    public void storeDataInArrays(){
        // Consulta para extraer los registros
        Cursor cursor = null;
        String query = "SELECT * FROM " + Utilities.TABLA_USUARIO;
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
                user_numb_counter++;
                user_numb.add(user_numb_counter + "");
                user_id.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
            }
            user_numb_counter = 0;
            cursor.close();
        }
    }

    // Se actualizan los registros de la lista
    public void fillUpdatedList() {
        // Inicializamos los arreglos
        user_numb = new ArrayList<>();
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();

        // Se extraen y capturan los datos
        storeDataInArrays();

        // Llamamos nuestro adapter para desplegar los datos extraidos y capturados
        customAdapter = new CustomUsersAdapter(MainUsersActivity.this, user_numb, user_id, user_name);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainUsersActivity.this));
    }

    // Cuando cargue la activity, se intentarán actualizar los registros de la lista
    @Override
    public void onResume(){
        super.onResume();
        fillUpdatedList();
    }
}