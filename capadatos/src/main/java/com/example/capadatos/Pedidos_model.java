package com.example.capadatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos_model {
    public static void  SavePedido(Context context, ArrayList<Pedidos> PEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            for(int i=0;i<PEDIDO.size();i++){
                Pedidos a = PEDIDO.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                contentValues.put("CODCLIENTE" , a.getmCliente());
                contentValues.put("CLIENTE" , a.getmNombre());
                contentValues.put("FECHA" , a.getmFecha());
                contentValues.put("MONTO" , a.getmPrecio());
                contentValues.put("ESTADO" , a.getmEstado());

                myDataBase.insert("PEDIDO", null, contentValues );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static void  SaveDetallePedido(Context context, ArrayList<Pedidos> DETPEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<DETPEDIDO.size();i++){
                Pedidos a = DETPEDIDO.get(i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("ARTICULO" , a.getmArticulo());
                contentValues.put("DESCRIPCION" , a.getmDescripcion());
                contentValues.put("CANTIDAD" , a.getmCantidad());
                contentValues.put("PRECIO" , a.getmPrecio());

                myDataBase.insert("PEDIDO_DETALLE", null, contentValues );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static List<Pedidos> getInfoPedidos(String basedir, Context context, Boolean hoy) {
        List<Pedidos> lista = new ArrayList<>();
        Cursor cursor;
        Integer i = 0;
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            cursor = myDataBase.query(true, "PEDIDO", null, "ESTADO IN (" + TextUtils.join(",", new String[]{"0", "1", "2", "3", "4"}) + ")", null, null, null, null, null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                    Pedidos tmp = new Pedidos();
                    tmp.setmIdPedido(cursor.getString(cursor.getColumnIndex("IDPEDIDO")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("VENDEDOR")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CODCLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("MONTO")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
                    tmp.setmEstado(cursor.getString(cursor.getColumnIndex("ESTADO")));
                    Cursor cursor2 = myDataBase.query(false, "PEDIDO_DETALLE", null, "IDPEDIDO"+ "=?", new String[] { cursor.getString(cursor.getColumnIndex("IDPEDIDO")) }, null, null, null, null);
                    cursor2.moveToFirst();

                    while(!cursor2.isAfterLast()) {
                        tmp.getDetalles().put("ID"+i,cursor2.getString(cursor2.getColumnIndex("IDPEDIDO")));
                        tmp.getDetalles().put("ARTICULO"+i,cursor2.getString(cursor2.getColumnIndex("ARTICULO")));
                        tmp.getDetalles().put("DESC"+i,cursor2.getString(cursor2.getColumnIndex("DESCRIPCION")));
                        tmp.getDetalles().put("CANT"+i,cursor2.getString(cursor2.getColumnIndex("CANTIDAD")));
                        tmp.getDetalles().put("TOTAL"+i,cursor2.getString(cursor2.getColumnIndex("PRECIO")));
                        i++;
                        cursor2.moveToNext();
                    }
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
    public static List<Pedidos> getDetalle(String basedir, Context context, String mIdPedido) {
        List<Pedidos> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(false, "PEDIDO_DETALLE", null, "IDPEDIDO"+ "=?", new String[] { mIdPedido }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    tmp.setmIdPedido(cursor.getString(cursor.getColumnIndex("IDPEDIDO")));
                    tmp.setmArticulo(cursor.getString(cursor.getColumnIndex("ARTICULO")));
                    tmp.setmDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    tmp.setmCantidad(cursor.getString(cursor.getColumnIndex("CANTIDAD")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("TOTAL")));
                    tmp.setmBonificado(cursor.getString(cursor.getColumnIndex("BONIFICADO")));
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
    public static List<Pedidos> getComentario(String basedir, Context context, String mIdPedido) {
        List<Pedidos> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "PEDIDO", null, "IDPEDIDO"+ "=?", new String[] { mIdPedido }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    tmp.setmComentario(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
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

    public static void borrar(Context contexto, String basedir) {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(basedir, contexto);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(false, "PEDIDO", null, "ESTADO IN (" + TextUtils.join(",", new String[]{ "3", "4"}) + ")", null, null, null, null, null);
            //Cursor cursor = myDataBase.query(false, "PEDIDO", null, "ESTADO"+ "=?", new String[] { "3" }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM PEDIDO WHERE IDPEDIDO= '"+cursor.getString(cursor.getColumnIndex("IDPEDIDO"))+"'");
                    SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM PEDIDO_DETALLE WHERE IDPEDIDO= '"+cursor.getString(cursor.getColumnIndex("IDPEDIDO"))+"'");
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
    }
}
