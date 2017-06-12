package com.example.alderhernandez.a4buy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capadatos.ManagerURI;
import com.example.capadatos.SQLiteHelper;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    Button acceder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            new SQLiteHelper(ManagerURI.getDirDb(),LoginActivity.this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        acceder = (Button)findViewById(R.id.acceder);
        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
