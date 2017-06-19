package com.example.alderhernandez.a4buy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capadatos.Cobros;
import com.example.capadatos.Cobros_model;
import com.example.capadatos.Notificaciones;

import java.text.DateFormat;
import java.util.ArrayList;

public class CobroInActivity extends AppCompatActivity {
    EditText mImporte,mObservacion;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ArrayList<Cobros> mCobro = new ArrayList<>();
    String Usuario,mCliente,nombreCliente;
    Integer contador = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro_in);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        Usuario = preferences.getString("NOMBRE","0");
        mCliente= preferences.getString("ClsSelected","0");
        nombreCliente = preferences.getString("NameClsSelected","");

        setTitle("COBRO: "+nombreCliente.toString());
        mImporte = (EditText) findViewById(R.id.crbImporte);
        mObservacion = (EditText) findViewById(R.id.crbObservacion);
        contador = preferences.getInt("CONTADOR2", 100);


        final Spinner spinner = (Spinner) findViewById(R.id.sp_transac);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"EFECTIVO","CHEQUE","TRANSFERENCIA"}));
        findViewById(R.id.btnSave_Cobro_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mImporte.getText()) || TextUtils.isEmpty(mObservacion.getText())){
                    new Notificaciones().Alert(CobroInActivity.this,"AVISO","HAY CAMPOS VACIOS...").setCancelable(false).setPositiveButton("OK", null).show();
                }else{
                    Cobros tmp = new Cobros();

                    Time now = new Time();
                    now.setToNow();
                    String sTime = now.format("%Y-%m-%d %H:%M:%S");

                    tmp.setmIdCobro(String.valueOf(contador));
                    tmp.setmCliente(mCliente);
                    tmp.setmRuta(Usuario);
                    tmp.setmImporte(mImporte.getText().toString().trim());
                    tmp.setmTipo(spinner.getSelectedItem().toString());
                    tmp.setmObservacion(mObservacion.getText().toString().trim());
                    tmp.setmFecha(sTime);
                    mCobro.add(tmp);
                    Cobros_model.SaveCobro(CobroInActivity.this,mCobro);

                    editor.putInt("CONTADOR2",Integer.valueOf(contador+1)).apply();
                    new Notificaciones().Alert(CobroInActivity.this,"COBRO","Informacion Guardada")
                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(CobroInActivity.this,MainActivity.class));
                            finish();
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(CobroInActivity.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


