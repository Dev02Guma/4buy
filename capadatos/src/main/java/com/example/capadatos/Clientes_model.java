package com.example.capadatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 12/06/2017.
 */

public class Clientes_model {
    public static List<Clientes> getClientes(String basedir, Context context) {
        List<Clientes> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Clientes tmp = new Clientes();
                    tmp.setmCodigo(cursor.getString(cursor.getColumnIndex("CODIGO")));
                    tmp.setmCedula(cursor.getString(cursor.getColumnIndex("CEDULA")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmSexo(cursor.getString(cursor.getColumnIndex("SEXO")));
                    tmp.setmDireccion(cursor.getString(cursor.getColumnIndex("DIRECCION")));
                    tmp.setmTelefono(cursor.getString(cursor.getColumnIndex("TELEFONO")));
                    tmp.setmTelefono(cursor.getString(cursor.getColumnIndex("MUNICIPIO")));

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
