package com.example.capadatos;

/**
 * Created by alder.hernandez on 12/06/2017.
 */

public class Clientes {
    String mCodigo,mCedula,mNombre,mSexo,mDireccion,mTelefono,mFecha,mMunicipio;
    public Clientes(){

    }

    public Clientes(String mCodigo, String mCedula, String mNombre, String mSexo, String mDireccion, String mTelefono, String mFecha, String mMunicipio) {
        this.mCodigo = mCodigo;
        this.mCedula = mCedula;
        this.mNombre = mNombre;
        this.mSexo = mSexo;
        this.mDireccion = mDireccion;
        this.mTelefono = mTelefono;
        this.mFecha = mFecha;
        this.mMunicipio = mMunicipio;
    }

    public String getmCodigo() {
        return mCodigo;
    }

    public void setmCodigo(String mCodigo) {
        this.mCodigo = mCodigo;
    }

    public String getmCedula() {
        return mCedula;
    }

    public void setmCedula(String mCedula) {
        this.mCedula = mCedula;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmSexo() {
        return mSexo;
    }

    public void setmSexo(String mSexo) {
        this.mSexo = mSexo;
    }

    public String getmDireccion() {
        return mDireccion;
    }

    public void setmDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public String getmTelefono() {
        return mTelefono;
    }

    public void setmTelefono(String mTelefono) {
        this.mTelefono = mTelefono;
    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getmMunicipio() {
        return mMunicipio;
    }

    public void setmMunicipio(String mMunicipio) {
        this.mMunicipio = mMunicipio;
    }
}
