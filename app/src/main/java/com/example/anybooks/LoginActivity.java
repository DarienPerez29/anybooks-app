package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout userLayout, passLayout;
    MaterialButton loginBtn, infoBtn;
    Dialog loginErrorDialog;

    DatabaseHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLayout = findViewById(R.id.username_layout);
        passLayout = findViewById(R.id.password_layout);
        loginBtn = findViewById(R.id.login_btn);
        infoBtn = findViewById(R.id.info_btn);

        conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        if (isUsersTableEmpty()) generateDefaultUser(); // Si la base de datos es nueva, entonces se genera un usuario por defecto

        loginErrorDialog = new Dialog(this);

        // Field watchers
        Objects.requireNonNull(userLayout.getEditText()).addTextChangedListener(userFieldWatcher);
        Objects.requireNonNull(passLayout.getEditText()).addTextChangedListener(passFieldWatcher);

        // Login button action
        loginBtn.setOnClickListener(v -> {
            if (ValidateLoginFields.emptyFields(userLayout, passLayout)) loginAttempt();
        });

        // Info button action
        infoBtn.setOnClickListener(v -> {
            Intent load = new Intent(LoginActivity.this, MainInfoActivity.class);
            startActivity(load);
        });

        // Filters
        Objects.requireNonNull(userLayout.getEditText()).setFilters(new InputFilter[]{
                new InputFilter.AllCaps() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase().replace(" ", "");
                    }
                }
        });
    }

    // Login attempt
    public void loginAttempt() {
        ValidateLoginFields validation = new ValidateLoginFields(this);
        if (validation.dataFields(userLayout, passLayout)) startNextAttempt();
        else showLoginError();
    }

    // Start dashboard activity
    public void startNextAttempt() {
        Intent load = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(load);
        finish();
    }

    // Show dialog if login fails
    public void showLoginError() {
        loginErrorDialog.setContentView(R.layout.dialog_login_error);
        loginErrorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton confirmBtn = loginErrorDialog.findViewById(R.id.confirm_deletion_ok_btn);

        confirmBtn.setOnClickListener(v -> loginErrorDialog.dismiss());

        loginErrorDialog.show();
    }

    // Función para insertar un usuario por default
    public void generateDefaultUser(){
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser insertados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_NOMBRE_USUARIO, "admin");
        values.put(Utilities.CAMPO_CONTRASENIA_USUARIO, "admin");

        db.insert(Utilities.TABLA_USUARIO, null, values); // Se realiza la query para insertar el usuario por defecto
        db.close();
    }

    // Validar tabla usuarios vacía
    public boolean isUsersTableEmpty(){
        // Consulta para extraer los registros
        Cursor cursor = null;
        String query = "SELECT * FROM " + Utilities.TABLA_USUARIO;
        SQLiteDatabase db = conn.getReadableDatabase();
        if (db != null) cursor = db.rawQuery(query, null);

        assert cursor != null;

        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    // Watcher for username field
    private final TextWatcher userFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(userLayout);
        }
    };

    // Watcher for password field
    private final TextWatcher passFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(passLayout);
        }
    };
}