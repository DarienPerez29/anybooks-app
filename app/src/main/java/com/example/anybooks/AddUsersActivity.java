package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddUsersActivity extends AppCompatActivity {

    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    ImageView backBtn;
    TextInputLayout user_name_layout, user_pass_layout;
    MaterialButton saveUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        getWindow().setStatusBarColor(getColor(R.color.section_users2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        user_name_layout = findViewById(R.id.user_name_layout);
        user_pass_layout = findViewById(R.id.user_pass_layout);
        saveUserBtn = findViewById(R.id.saveUserBtn);

        // Watchers para los campos
        Objects.requireNonNull(user_name_layout.getEditText()).addTextChangedListener(userNameWatcher);
        Objects.requireNonNull(user_pass_layout.getEditText()).addTextChangedListener(userPassWatcher);

        saveUserBtn.setOnClickListener(v -> {
            if (checkEmptyFields()) saveUser();
        });

        // Filters
        Objects.requireNonNull(user_name_layout.getEditText()).setFilters(new InputFilter[]{
                new InputFilter.AllCaps() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase().replace(" ", "");
                    }
                }
        });
    }

    // Función para agregar un usuario
    public void saveUser(){
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser insertados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_NOMBRE_USUARIO, Objects.requireNonNull(user_name_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_CONTRASENIA_USUARIO, Objects.requireNonNull(user_pass_layout.getEditText()).getText().toString().trim());

        long result = db.insert(Utilities.TABLA_USUARIO, null, values); // Se realiza la query para insertar datos en la tabla correspondiente

        // Se valida si la inserción ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Usuario añadido", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // =============================
    // FUNCIONES COMPLEMENTARIAS
    // =============================

    // Función para validar que los campos no estén vacios
    public boolean checkEmptyFields(){
        String name = Objects.requireNonNull(user_name_layout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(user_pass_layout.getEditText()).getText().toString().trim();

        if (!name.isEmpty() && !pass.isEmpty()) return true;

        if (name.isEmpty()) user_name_layout.setError(EMPTY_ERR);
        else user_name_layout.setErrorEnabled(false);

        if (pass.isEmpty()) user_pass_layout.setError(EMPTY_ERR);
        else user_pass_layout.setErrorEnabled(false);

        return false;
    }

    // ========================
    // WATCHERS
    // ========================

    // Watcher para campo nombre
    private final TextWatcher userNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(user_name_layout);
        }
    };

    // Watcher para campo contrasenia
    private final TextWatcher userPassWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(user_pass_layout);
        }
    };
}