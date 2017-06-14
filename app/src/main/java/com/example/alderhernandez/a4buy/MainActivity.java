package com.example.alderhernandez.a4buy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alderhernandez.a4buy.Adapters.Clientes_Leads;
import com.example.capadatos.Clientes;
import com.example.capadatos.Clientes_model;
import com.example.capadatos.ManagerURI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Clientes> objects;
    private Clientes_Leads lbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("MIS CLIENTES");
        listView = (ListView) findViewById(R.id.lstClientes);

        objects = Clientes_model.getClientes(ManagerURI.getDirDb(), MainActivity.this);
        lbs = new Clientes_Leads(this, objects);

        listView.setAdapter(lbs);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence[]items = { "PEDIDO","COBRO","INFORMACION"};
                new AlertDialog.Builder(parent.getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(items[0])){
                            startActivity(new Intent(MainActivity.this,Borrar.class));
                        }else if (items[which].equals(items[1])){
                            Toast.makeText(MainActivity.this, "COBRO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
            }
        });
    }
}
