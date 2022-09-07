package com.example.anybooks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.anybooks.utilities.Utilities;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREAR_TABLA_LIBRO);
        db.execSQL(Utilities.CREAR_TABLA_ARTICULO);
//        db.execSQL(Utilities.CREAR_TABLA_MULTIMEDIA);
//        db.execSQL(Utilities.CREAR_TABLA_PRESTAMO);
        db.execSQL(Utilities.CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA_LIBRO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA_ARTICULO);
//        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA_MULTIMEDIA);
//        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA_PRESTAMO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA_USUARIO);
        onCreate(db);
    }
}
