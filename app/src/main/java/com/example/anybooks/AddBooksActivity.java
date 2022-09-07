package com.example.anybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddBooksActivity extends AppCompatActivity {

    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    ImageView backBtn;
    TextInputLayout title_layout, author_layout, year_layout, language_layout, editorial_layout, pages_layout;
    MaterialButton saveBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        getWindow().setStatusBarColor(getColor(R.color.section_books2));

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

        title_layout = findViewById(R.id.title_layout);
        author_layout = findViewById(R.id.author_layout);
        year_layout = findViewById(R.id.year_layout);
        language_layout = findViewById(R.id.language_layout);
        editorial_layout = findViewById(R.id.editorial_layout);
        pages_layout = findViewById(R.id.pages_layout);
        saveBookBtn = findViewById(R.id.saveBookBtn);

        // Watchers para los campos
        Objects.requireNonNull(title_layout.getEditText()).addTextChangedListener(titleFieldWatcher);
        Objects.requireNonNull(author_layout.getEditText()).addTextChangedListener(authorFieldWatcher);
        Objects.requireNonNull(year_layout.getEditText()).addTextChangedListener(yearFieldWatcher);
        Objects.requireNonNull(language_layout.getEditText()).addTextChangedListener(languageFieldWatcher);
        Objects.requireNonNull(editorial_layout.getEditText()).addTextChangedListener(editorialFieldWatcher);
        Objects.requireNonNull(pages_layout.getEditText()).addTextChangedListener(pagesFieldWatcher);

        saveBookBtn.setOnClickListener(v -> {
            if (checkEmptyFields()) saveBook();
        });
    }

    // Función para agregar un libro
    public void saveBook(){
        DatabaseHelper conn = new DatabaseHelper(this, "AnyBooks", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Se añaden los datos para ser insertados por la query posterior
        ContentValues values = new ContentValues();
        values.put(Utilities.CAMPO_TITULO_LIBRO, Objects.requireNonNull(title_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_AUTOR_LIBRO, Objects.requireNonNull(author_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_ANIO_LIBRO, Objects.requireNonNull(year_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_IDIOMA_LIBRO, Objects.requireNonNull(language_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_EDITORIAL_LIBRO, Objects.requireNonNull(editorial_layout.getEditText()).getText().toString().trim());
        values.put(Utilities.CAMPO_PAGINAS_LIBRO, Objects.requireNonNull(pages_layout.getEditText()).getText().toString().trim());

        long result = db.insert(Utilities.TABLA_LIBRO, null, values); // Se realiza la query para insertar datos en la tabla correspondiente

        // Se valida si la inserción ha sido realizada
        if (result == -1) {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(this, "Libro añadido", Toast.LENGTH_SHORT).show();
            db.close();
            onBackPressed();
        }
    }

    // =============================
    // FUNCIONES COMPLEMENTARIAS
    // =============================

    // Función para validar que los campos no estén vacios
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