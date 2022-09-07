package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UpdatePapersActivity extends AppCompatActivity {

    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    ImageView backBtn;
    TextInputLayout title_layout, author_layout, year_layout, language_layout;
    MaterialButton updatePaperBtn, deletePaperBtn;
    TextView subtitle;
    Dialog confirmDeletionDialog;
    static boolean deletion_result;

    String id_paper, title, author, year, language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_papers);

        getWindow().setStatusBarColor(getColor(R.color.section_papers2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        title_layout = findViewById(R.id.title_layout_update);
        author_layout = findViewById(R.id.author_layout_update);
        year_layout = findViewById(R.id.year_layout_update);
        language_layout = findViewById(R.id.language_layout_update);
        updatePaperBtn = findViewById(R.id.updatePaperBtn);
        deletePaperBtn = findViewById(R.id.deletePaperBtn);

        subtitle = findViewById((R.id.subtitle));

        confirmDeletionDialog = new Dialog(this);

        getSetIntentData();

        // Field watchers
        Objects.requireNonNull(title_layout.getEditText()).addTextChangedListener(titleFieldWatcher);
        Objects.requireNonNull(author_layout.getEditText()).addTextChangedListener(authorFieldWatcher);
        Objects.requireNonNull(year_layout.getEditText()).addTextChangedListener(yearFieldWatcher);
        Objects.requireNonNull(language_layout.getEditText()).addTextChangedListener(languageFieldWatcher);

        // Botón para actualizar registro
        updatePaperBtn.setOnClickListener(v -> {
            if (checkEmptyFields()) updatePaper();
        });

        // Botón para borrar registro
        deletePaperBtn.setOnClickListener(v -> {
            validateDeletion();
        });
    }

    // Se obtienen los datos que se mandan por la activity anterior a través del intent
    void getSetIntentData() {
        // Se valida que el intent esté mandando datos adicionales
        if (getIntent().hasExtra("id_paper") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("year") &&
                getIntent().hasExtra("language")){

            // Obteniendo datos del intent
            id_paper = getIntent().getStringExtra("id_paper");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            year = getIntent().getStringExtra("year");
            language = getIntent().getStringExtra("language");

            //Seteando datos del intent
            Objects.requireNonNull(title_layout.getEditText()).setText(title);
            Objects.requireNonNull(author_layout.getEditText()).setText(author);
            Objects.requireNonNull(year_layout.getEditText()).setText(year);
            Objects.requireNonNull(language_layout.getEditText()).setText(language);
            subtitle.setText(title); // Se actualiza el subtítulo con el título del artículo a editar
        } else {
            Toast.makeText(this, "Sin datos del registro", Toast.LENGTH_SHORT).show();
        }
    }

    // Función para actualizar el articulo seleccionado
    public void updatePaper() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser actualizados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_TITULO_ARTICULO, Objects.requireNonNull(title_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_AUTOR_ARTICULO, Objects.requireNonNull(author_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_ANIO_ARTICULO, Objects.requireNonNull(year_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_IDIOMA_ARTICULO, Objects.requireNonNull(language_layout.getEditText()).getText().toString().trim());

        long result = db.update(Utilities.TABLA_ARTICULO, values, Utilities.CAMPO_ID_ARTICULO + "=?", new String[]{id_paper}); // Se realiza la query para actualizar datos en el registro

        // Se valida si la actualización ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Articulo actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // Función para eliminar el articulo seleccionado
    public void deletePaper() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        long result = db.delete(Utilities.TABLA_ARTICULO, Utilities.CAMPO_ID_ARTICULO + "=?", new String[]{id_paper});

        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Artículo eliminado", Toast.LENGTH_SHORT).show();
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
            deletePaper();
        });

        // Botón para cancelar
        cancelBtn.setOnClickListener(v -> {
            confirmDeletionDialog.dismiss();
        });

        confirmDeletionDialog.show();
    }

    // Función para validar que los campos no estén vacios
    public boolean checkEmptyFields(){
        String title = Objects.requireNonNull(title_layout.getEditText()).getText().toString().trim();
        String author = Objects.requireNonNull(author_layout.getEditText()).getText().toString().trim();
        String year = Objects.requireNonNull(year_layout.getEditText()).getText().toString().trim();
        String language = Objects.requireNonNull(language_layout.getEditText()).getText().toString().trim();

        if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !language.isEmpty()) return true;

        if (title.isEmpty()) title_layout.setError(EMPTY_ERR);
        else title_layout.setErrorEnabled(false);

        if (author.isEmpty()) author_layout.setError(EMPTY_ERR);
        else author_layout.setErrorEnabled(false);

        if (year.isEmpty()) year_layout.setError(EMPTY_ERR);
        else year_layout.setErrorEnabled(false);

        if (language.isEmpty()) language_layout.setError(EMPTY_ERR);
        else language_layout.setErrorEnabled(false);

        return false;
    }

    // ========================
    // WATCHERS
    // ========================

    // Watcher para campo titulo
    private final TextWatcher titleFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(title_layout);
        }
    };

    // Watcher para campo autor
    private final TextWatcher authorFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(author_layout);
        }
    };

    // Watcher para campo año
    private final TextWatcher yearFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(year_layout);
        }
    };

    // Watcher para campo idioma
    private final TextWatcher languageFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(language_layout);
        }
    };
}