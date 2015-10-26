package com.example.richard.mudateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Richard on 10/25/2015.
 */
public class DBManagerEstate {
    public static final String TABLE_NAME = "estate";

    public static final String ID = "_id";
    public static final String DIRECCION = "address";
    public static final String NOMBRE = "name";
    public static final String VALOR = "value";
    public static final String TIPO = "type";
    public static final String ESTADO = "state";
    public static final String HABITACIONES = "rooms";
    public static final String BANOS = "baths";
    public static final String COCINAS = "kitchens";
    public static final String CREATE_TABLE = "create table " +
            TABLE_NAME
            + " (" + ID + " integer primary key autoincrement,"
            + DIRECCION + " text not null,"
            + NOMBRE + " text not null,"
            + VALOR + " double not null,"
            + TIPO + " text not null,"
            + ESTADO + " boolean not null,"
            + HABITACIONES + " integer not null,"
            + BANOS + " integer not null,"
            + COCINAS + " integer not null"
            + ");";
    public static final String DROP_TABLE = "drop table if exist " + TABLE_NAME + ";";


    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManagerEstate(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String direccion, String nombre, Double value, String tipo, Boolean estado, int habitaciones, int banos, int cocinas) {
        ContentValues valores = new ContentValues();
        valores.put(DIRECCION, direccion);
        valores.put(NOMBRE, nombre);
        valores.put(VALOR, value);
        valores.put(TIPO, tipo);
        valores.put(ESTADO, estado);
        valores.put(HABITACIONES, habitaciones);
        valores.put(BANOS, banos);
        valores.put(COCINAS, cocinas);
        return valores;
    }

    public boolean insertar(String direccion, String nombre, Double value, String tipo, Boolean estado, int habitaciones, int banos, int cocinas) {
        db.insert(TABLE_NAME, null, generarContentValues(direccion, nombre, value, tipo, estado, habitaciones, banos, cocinas));
        return true;
    }

    public void eliminar(int id) {
        db.delete(TABLE_NAME, ID + "=?", new String[]{Integer.toString(id)});
    }

    public boolean actualizar(int id, String direccion, String nombre, Double value, String tipo, Boolean estado, int habitaciones, int banos, int cocinas) {
        db.update(TABLE_NAME, generarContentValues(direccion, nombre, value, tipo, estado, habitaciones, banos, cocinas), ID + "=?", new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor all(boolean b){
        String[] columnas = new String[]{ID, DIRECCION, NOMBRE, VALOR, TIPO, ESTADO, HABITACIONES, BANOS, COCINAS};
        return db.query(TABLE_NAME,columnas, null, null, null, null, null);
    }

    public Cursor getData(Integer id) {
        String[] columnas = new String[]{ID, DIRECCION, NOMBRE, VALOR, TIPO, ESTADO, HABITACIONES, BANOS, COCINAS};
        return db.query(TABLE_NAME, columnas, ID + "=?", new String[]{ Integer.toString(id) }, null, null, null);
    }
}