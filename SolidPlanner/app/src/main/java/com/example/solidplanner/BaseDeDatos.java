package com.example.solidplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDeDatos extends SQLiteOpenHelper {

    private String sql = "create table eventos("+
            "idEvento int primary key autoincrement, "+
            "nombreEvento varchar(40), "+
            "fechadesde date, "+
            "horadesde time, "+
            "fechahasta date, "+
            "horahasta time, "+
            "descripcion varchar(100))";

    public BaseDeDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
