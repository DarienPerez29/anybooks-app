package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UpdateUsersActivity extends AppCompatActivity {

    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    ImageView backBtn;
    TextInputLayout user_name_layout, user_pass_layout;
    MaterialButton updateUserBtn, deleteUserBtn;
    TextView subtitle;
    Dialog confirmDeletionDialog;
    static boolean deletion_result;

    String id_user, user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);

        getWindow().setStatusBarColor(getColor(R.color.section_users2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        user_name_layout = findViewById(R.id.user_name_layout_update);
        user_pass_layout = findViewById(R.id.user_pass_layout_update);
        updateUserBtn = findViewById(R.id.updateUserBtn);
        deleteUserBtn = findViewById(R.id.deleteUserBtn);

        subtitle = findViewById((R.id.subtitle));

        confirmDeletionDialog = new Dialog(this);

        getSetIntentData();

        // Field watchers
        Objects.requireNonNull(user_name_layout.getEditText()).addTextChangedListener(userNameWatcher);

        // Botón para actualizar registro
        updateUserBtn.setOnClickListener(v -> {
            if (checkEmptyFields() == 1) updateUserWithoutPass();
            else if (checkEmptyFields() == 2) updateUserWithPass();
        });

        // Botón para borrar registro
        deleteUserBtn.setOnClickListener(v -> {
            validateDeletion();
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

    // Se obtienen los datos que se mandan por la activity anterior a través del intent
    void getSetIntentData() {
        // Se valida que el intent esté mandando datos adicionales
        if (getIntent().hasExtra("id_user") && getIntent().hasExtra("user_name")){

            // Obteniendo datos del intent
            id_user = getIntent().getStringExtra("id_user");
            user_name = getIntent().getStringExtra("user_name");

            //Seteando datos del intent
            Objects.requireNonNull(user_name_layout.getEditText()).setText(user_name);
            subtitle.setText(user_name); // Se actualiza el subtítulo con el nombre del usuario a editar
        } else {
            Toast.makeText(this, "Sin datos del registro", Toast.LENGTH_SHORT).show();
        }
    }

    // Función para actualizar sin contraseña
    public void updateUserWithoutPass() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser actualizados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_NOMBRE_USUARIO, Objects.requireNonNull(user_name_layout.getEditText()).getText().toString().trim());

        long result = db.update(Utilities.TABLA_USUARIO, values, Utilities.CAMPO_ID_USUARIO + "=?", new String[]{id_user}); // Se realiza la query para actualizar datos en el registro

        // Se valida si la actualización ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // Función para actualizar con contraseña
    public void updateUserWithPass() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser actualizados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_NOMBRE_USUARIO, Objects.requireNonNull(user_name_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_CONTRASENIA_USUARIO, Objects.requireNonNull(user_pass_layout.getEditText()).getText().toString().trim());

        long result = db.update(Utilities.TABLA_USUARIO, values, Utilities.CAMPO_ID_USUARIO + "=?", new String[]{id_user}); // Se realiza la query para actualizar datos en el registro

        // Se valida si la actualización ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // Función para eliminar el usuario seleccionado
    public void deleteUser() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        long result = db.delete(Utilities.TABLA_USUARIO, Utilities.CAMPO_ID_USUARIO + "=?", new String[]{id_user});

        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
            db.close();
            finish();
        }
    }

    // =============================
    // FUNCIONES COMPLEMENTARIAS
    // =============================

    // Se valida que el usuario confirme la eliminación del registro con un dialog
    public void validateDeletion() {
        deletion_result = false;
        confirmDeletionDialog.setContentView(R.layout.dialog_confirm_deletion);
        confirmDeletionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton deleteBtn = confirmDeletionDialog.findViewById(R.id.confirm_deletion_ok_btn);
        MaterialButton cancelBtn = confirmDeletionDialog.findViewById(R.id.confirm_deletion_cancel_btn);

        // Botón para confirmar
        deleteBtn.setOnClickListener(v -> {
            confirmDeletionDialog.dismiss();
            deleteUser();
        });

        // Botón para cancelar
        cancelBtn.setOnClickListener(v -> {
            confirmDeletionDialog.dismiss();
        });

        confirmDeletionDialog.show();
    }

    // Función para validar que los campos no estén vacios
    public int checkEmptyFields(){
        String name = Objects.requireNonNull(user_name_layout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(user_pass_layout.getEditText()).getText().toString().trim();

        if (!name.isEmpty() && !pass.isEmpty()) return 2;
        if (!name.isEmpty()) return 1;

        if (name.isEmpty()) user_name_layout.setError(EMPTY_ERR);
        else user_name_layout.setErrorEnabled(false);

        return 0;
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
}