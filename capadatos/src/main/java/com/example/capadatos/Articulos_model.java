package com.example.capadatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Articulos_model {

    public static List<Articulo> getArticulos(String basedir, Context context) {
        List<Articulo> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ARTICULO", null, null, null, null, null, null, null);
            Log.d("", "getArticulos: "+cursor.getCount());
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Articulo tmp = new Articulo();
                    tmp.setmCodigo(cursor.getString(cursor.getColumnIndex("IDARTICULO")));
                    tmp.setmName(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("PRECIO")));
                    tmp.setmExistencia(cursor.getString(cursor.getColumnIndex("EXISTENCIA")));
                    tmp.setmCategoria(cursor.getString(cursor.getColumnIndex("CATEGORIA")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
}