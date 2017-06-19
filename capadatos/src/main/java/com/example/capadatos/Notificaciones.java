package com.example.capadatos;

import android.app.Activity;
import android.support.v7.app.AlertDialog;



public class Notificaciones {


    public AlertDialog.Builder Alert(Activity activity, String Titulos, String Mensaje){
        return new AlertDialog.Builder(activity).setTitle(Titulos).setMessage(Mensaje).setCancelable(false).setPositiveButton("OK",null);
    }
}