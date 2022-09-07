package com.example.anybooks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.anybooks.utilities.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ValidateLoginFields {
    private static final String EMPTY_ERR = "Este campo no puede estar vacío";
    Context context;
    static DatabaseHelper conn;

    public ValidateLoginFields (Context context) {
        this.context = context;
        conn = new DatabaseHelper(context, "AnyBooks", null, 1);
    }

    // Verify all data
    public boolean dataFields(TextInputLayout userLayout, TextInputLayout passLayout) {
        String user = Objects.requireNonNull(userLayout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(passLayout.getEditText()).getText().toString().trim();

        return isUserAndPassCorrect(user, pass);
    }

    // Verify if fields are empty
    public static boolean emptyFields(TextInputLayout userLayout, TextInputLayout passLayout) {
        String user = Objects.requireNonNull(userLayout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(passLayout.getEditText()).getText().toString().trim();

        if (!user.isEmpty() && !pass.isEmpty()) return true;

        if (user.isEmpty()) userLayout.setError(EMPTY_ERR);
        else userLayout.setErrorEnabled(false);

        if (pass.isEmpty()) passLayout.setError(EMPTY_ERR);
        else passLayout.setErrorEnabled(false);

        return false;
    }

    // Clear error watcher
    public static void clearEmptyErrorWatcher(TextInputLayout layout) {
        String content = Objects.requireNonNull(layout.getEditText()).getText().toString().trim();
        if (!content.isEmpty()) layout.setErrorEnabled(false);
    }

    // Validar credenciales de acceso
    public boolean isUserAndPassCorrect(String user, String pass){
        String pass_stored = "";
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] params = {user};
        String[] fields = {Utilities.CAMPO_CONTRASENIA_USUARIO};

        try {
            Cursor cursor = db.query(Utilities.TABLA_USUARIO, fields, Utilities.CAMPO_NOMBRE_USUARIO + "=?", params, null, null, null);
            cursor.moveToFirst();

            pass_stored = cursor.getString(0);

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show();
        }
        return pass_stored.equals(pass);
    }
}
