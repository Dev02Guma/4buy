package com.example.capadatos;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Articulo {
    private String mCodigo;
    private String mName;
    private String mExistencia;

    private String mPrecio;
    private String mCategoria;

    public Articulo(String mCodigo, String mName, String mExistencia, String mPrecio, String mCategoria) {
        this.mCodigo = mCodigo;
        this.mName = mName;
        this.mExistencia = mExistencia;
        this.mPrecio = mPrecio;
        this.mCategoria = mCategoria;
    }

    public Articulo(){

    }

    public String getmCodigo() {
        return mCodigo;
    }

    public void setmCodigo(String mCodigo) {
        this.mCodigo = mCodigo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmExistencia() {
        return mExistencia;
    }

    public void setmExistencia(String mExistencia) {
        this.mExistencia = mExistencia;
    }

    public String getmPrecio() {
        return mPrecio;
    }

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }

    public String getmCategoria() {
        return mCategoria;
    }

    public void setmCategoria(String mCategoria) {
        this.mCategoria = mCategoria;
    }
}
