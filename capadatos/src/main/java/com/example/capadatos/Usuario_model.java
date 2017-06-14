package com.example.capadatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 12/06/2017.
 */

public class Usuario_model {

    public static List<Usuario> Login(String mUsuario, String Pass, Context context) {

        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "USUARIO", null, "USUARIO"+"=?" + " AND "+ " PASS" +"=?", new String[] { mUsuario,Pass }, null, null, null, null);


            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Usuario tmp = new Usuario();
                    tmp.setmIdUsuario(cursor.getInt(cursor.getColumnIndex("IDUSUARIO")));
                    tmp.setmUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
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
