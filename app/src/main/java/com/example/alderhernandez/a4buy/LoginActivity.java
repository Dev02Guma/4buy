package com.example.alderhernandez.a4buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capadatos.ManagerURI;
import com.example.capadatos.Notificaciones;
import com.example.capadatos.SQLiteHelper;
import com.example.capadatos.Usuario;
import com.example.capadatos.Usuario_model;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    boolean loged;
    Button acceder;
    TextView usuario,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        loged = preferences.getBoolean("logueado", false);

        acceder = (Button)findViewById(R.id.acceder);
        usuario = (TextView) findViewById(R.id.txtUsuario);
        pass = (TextView) findViewById(R.id.txtPassword);
        if (loged){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(usuario.getText())){
                    usuario.setError("USUARIO REQUERIDO");
                }if(TextUtils.isEmpty(pass.getText())){
                    pass.setError("CONTRASEÑA REQUERIDA");
                    return;
                }else{
                    List<Usuario> login = Usuario_model.Login(usuario.getText().toString(),pass.getText().toString(),LoginActivity.this);
                    if (login.size()>0) {
                        editor.putString("USUARIO",login.get(0).getmUsuario());
                        editor.putString("NOMBRE",login.get(0).getmNombre());
                        editor.putString("IDUSUARIO",login.get(0).getmIdUsuario().toString());
                        editor.putInt("CONTADOR",100);
                        editor.putBoolean("logueado", !loged);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else{
                        new Notificaciones().Alert(LoginActivity.this,"AVISO","USUARIO O CONTRASEÑA INCORRECTOS").show();
                    }
                }
            }
        });
    }
}
