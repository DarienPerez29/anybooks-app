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

public class UpdateBooksActivity extends AppCompatActivity {

    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    ImageView backBtn;
    TextInputLayout title_layout, author_layout, year_layout, language_layout, editorial_layout, pages_layout;
    MaterialButton updateBookBtn, deleteBookBtn;
    TextView subtitle;
    Dialog confirmDeletionDialog;
    static boolean deletion_result;

    String id_book, title, author, year, language, editorial, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_books);

        getWindow().setStatusBarColor(getColor(R.color.section_books2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        title_layout = findViewById(R.id.title_layout_update);
        author_layout = findViewById(R.id.author_layout_update);
        year_layout = findViewById(R.id.year_layout_update);
        language_layout = findViewById(R.id.language_layout_update);
        editorial_layout = findViewById(R.id.editorial_layout_update);
        pages_layout = findViewById(R.id.pages_layout_update);
        updateBookBtn = findViewById(R.id.updateBookBtn);
        deleteBookBtn = findViewById(R.id.deleteBookBtn);

        subtitle = findViewById((R.id.subtitle));

        confirmDeletionDialog = new Dialog(this);

        getSetIntentData();

        // Field watchers
        Objects.requireNonNull(title_layout.getEditText()).addTextChangedListener(titleFieldWatcher);
        Objects.requireNonNull(author_layout.getEditText()).addTextChangedListener(authorFieldWatcher);
        Objects.requireNonNull(year_layout.getEditText()).addTextChangedListener(yearFieldWatcher);
        Objects.requireNonNull(language_layout.getEditText()).addTextChangedListener(languageFieldWatcher);
        Objects.requireNonNull(editorial_layout.getEditText()).addTextChangedListener(editorialFieldWatcher);
        Objects.requireNonNull(pages_layout.getEditText()).addTextChangedListener(pagesFieldWatcher);

        // Botón para actualizar registro
        updateBookBtn.setOnClickListener(v -> {
            if (checkEmptyFields()) updateBook();
        });

        // Botón para borrar registro
        deleteBookBtn.setOnClickListener(v -> {
            validateDeletion();
        });
    }

    // Se obtienen los datos que se mandan por la activity anterior a través del intent
    void getSetIntentData() {
        // Se valida que el intent esté mandando datos adicionales
        if (getIntent().hasExtra("id_book") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("year") &&
                getIntent().hasExtra("language") && getIntent().hasExtra("editorial") &&
                getIntent().hasExtra("pages")){

            // Obteniendo datos del intent
            id_book = getIntent().getStringExtra("id_book");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            year = getIntent().getStringExtra("year");
            language = getIntent().getStringExtra("language");
            editorial = getIntent().getStringExtra("editorial");
            pages = getIntent().getStringExtra("pages");

            //Seteando datos del intent
            Objects.requireNonNull(title_layout.getEditText()).setText(title);
            Objects.requireNonNull(author_layout.getEditText()).setText(author);
            Objects.requireNonNull(year_layout.getEditText()).setText(year);
            Objects.requireNonNull(language_layout.getEditText()).setText(language);
            Objects.requireNonNull(editorial_layout.getEditText()).setText(editorial);
            Objects.requireNonNull(pages_layout.getEditText()).setText(pages);
            subtitle.setText(title); // Se actualiza el subtítulo con el título del libro a editar
        } else {
            Toast.makeText(this, "Sin datos del registro", Toast.LENGTH_SHORT).show();
        }
    }

    // Función para actualizar el libro seleccionado
    public void updateBook() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser actualizados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_TITULO_LIBRO, Objects.requireNonNull(title_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_AUTOR_LIBRO, Objects.requireNonNull(author_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_ANIO_LIBRO, Objects.requireNonNull(year_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_IDIOMA_LIBRO, Objects.requireNonNull(language_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_EDITORIAL_LIBRO, Objects.requireNonNull(editorial_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_PAGINAS_LIBRO, Objects.requireNonNull(pages_layout.getEditText()).getText().toString().trim());

        long result = db.update(Utilities.TABLA_LIBRO, values, Utilities.CAMPO_ID_LIBRO + "=?", new String[]{id_book}); // Se realiza la query para actualizar datos en el registro

        // Se valida si la actualización ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // Función para eliminar el libro seleccionado
    public void deleteBook() {
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        long result = db.delete(Utilities.TABLA_LIBRO, Utilities.CAMPO_ID_LIBRO + "=?", new String[]{id_book});

        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Libro eliminado", Toast.LENGTH_SHORT).show();
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
            deleteBook();
        });

        // Botón para cancelar
        cancelBtn.setOnClickListener(v -> {
            confirmDeletionDialog.dismiss();
        });

        confirmDeletionDialog.show();
    }

    // Se valida que los campos no estén vacios
    public boolean checkEmptyFields(){
        String title = Objects.requireNonNull(title_layout.getEditText()).getText().toString().trim();
        String author = Objects.requireNonNull(author_layout.getEditText()).getText().toString().trim();
        String year = Objects.requireNonNull(year_layout.getEditText()).getText().toString().trim();
        String language = Objects.requireNonNull(language_layout.getEditText()).getText().toString().trim();
        String editorial = Objects.requireNonNull(editorial_layout.getEditText()).getText().toString().trim();
        String pages = Objects.requireNonNull(pages_layout.getEditText()).getText().toString().trim();

        if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !language.isEmpty() && !editorial.isEmpty() && !pages.isEmpty()) return true;

        if (title.isEmpty()) title_layout.setError(EMPTY_ERR);
        else title_layout.setErrorEnabled(false);

        if (author.isEmpty()) author_layout.setError(EMPTY_ERR);
        else author_layout.setErrorEnabled(false);

        if (year.isEmpty()) year_layout.setError(EMPTY_ERR);
        else year_layout.setErrorEnabled(false);

        if (language.isEmpty()) language_layout.setError(EMPTY_ERR);
        else language_layout.setErrorEnabled(false);

        if (editorial.isEmpty()) editorial_layout.setError(EMPTY_ERR);
        else editorial_layout.setErrorEnabled(false);

        if (pages.isEmpty()) pages_layout.setError(EMPTY_ERR);
        else pages_layout.setErrorEnabled(false);

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

    // Watcher para campo editorial
    private final TextWatcher editorialFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(editorial_layout);
        }
    };

    // Watcher para campo páginas
    private final TextWatcher pagesFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ValidateLoginFields.clearEmptyErrorWatcher(pages_layout);
        }
    };
}