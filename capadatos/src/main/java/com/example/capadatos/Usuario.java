package com.example.capadatos;

/**
 * Created by alder.hernandez on 12/06/2017.
 */

public class Usuario {
    Integer mIdUsuario;
    String mUsuario,mNombre,mPass,mEstado;

    public Usuario(){

    }

    public Usuario(Integer mIdUsuario, String mUsuario, String mNombre, String mPass, String mEstado) {
        this.mIdUsuario = mIdUsuario;
        this.mUsuario = mUsuario;
        this.mNombre = mNombre;
        this.mPass = mPass;
        this.mEstado = mEstado;
    }

    public Integer getmIdUsuario() {
        return mIdUsuario;
    }

    public void setmIdUsuario(Integer mIdUsuario) {
        this.mIdUsuario = mIdUsuario;
    }

    public String getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }

    public String getmEstado() {
        return mEstado;
    }

    public void setmEstado(String mEstado) {
        this.mEstado = mEstado;
    }
}
