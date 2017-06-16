package com.example.alderhernandez.a4buy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard2:
                    final CharSequence[]items = { "SINCRONIZAR","HISTORIAL PEDIDOS","HISTORIAL COBROS","DESCONECTAR"};
                    new AlertDialog.Builder(MainActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (items[which].equals(items[0])){
                                Toast.makeText(MainActivity.this, "DATOS SINCRONIZADOS", Toast.LENGTH_SHORT).show();
                            }else if (items[which].equals(items[1])){
                                startActivity(new Intent(MainActivity.this,BandejaPedidosActivity.class));
                            }else if (items[which].equals(items[2])){
                                Toast.makeText(MainActivity.this, "COBROS", Toast.LENGTH_SHORT).show();
                            }else if (items[which].equals(items[3])){
                                editor.putBoolean("logueado", false).commit();
                                editor.apply();
                                finish();
                            }
                        }
                    }).create().show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle("MIS CLIENTES");
        listView = (ListView) findViewById(R.id.lstClientes);

        objects = Clientes_model.getClientes(ManagerURI.getDirDb(), MainActivity.this);
        lbs = new Clientes_Leads(this, objects);

        listView.setAdapter(lbs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {
                final CharSequence[]items = { "PEDIDO","COBRO","INFORMACION"};
                new AlertDialog.Builder(parent.getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(items[0])){
                            final Clientes mCLientes = (Clientes) parent.getItemAtPosition(position);
                            editor.putString("ClsSelected",mCLientes.getmCodigo());
                            editor.putString("NameClsSelected",mCLientes.getmNombre());
                            editor.apply();
                            //startActivity(new Intent(MainActivity.this,PedidoActivity.class));
                            startActivity(new Intent(MainActivity.this,PedidoActivity2.class));
                        }else if (items[which].equals(items[1])){
                            Toast.makeText(MainActivity.this, "COBRO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
            }
        });
    }

}
