package com.example.alderhernandez.a4buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alderhernandez.a4buy.Adapters.Pedidos_Leads;
import com.example.capadatos.ManagerURI;
import com.example.capadatos.Notificaciones;
import com.example.capadatos.Pedidos;
import com.example.capadatos.Pedidos_model;

import java.util.ArrayList;
import java.util.List;

public class BandejaPedidosActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Pedidos> fList;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    TextView mTotal;
    float mCalTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_pedidos);

        setTitle("PEDIDOS DEL DIA");
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        mTotal = (TextView) findViewById(R.id.txtTotalPedidos);
        fList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView_pedidos);

        List<Pedidos> lstObj = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(), BandejaPedidosActivity.this,false);
        if (lstObj.size() == 0){
            new Notificaciones().Alert(BandejaPedidosActivity.this,"AVISO","NO HAY PEDIDOS...").setCancelable(false).setPositiveButton("OK", null).show();
        }else{
            for(Pedidos obj : lstObj) {
                fList.add(obj);
                mCalTotal += Float.parseFloat(obj.getmPrecio());
            }
            //Toast.makeText(this, fList.get(0).toString(), Toast.LENGTH_SHORT).show();
            mTotal.setText("C$ " + mCalTotal);
            listView.setAdapter(new Pedidos_Leads(this, fList));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater li = LayoutInflater.from(BandejaPedidosActivity.this);
                final Pedidos pedid = (Pedidos) adapterView.getItemAtPosition(i);
                String idPedido = ((Pedidos) adapterView.getItemAtPosition(i)).getmIdPedido();
                String Cliente = ((Pedidos) adapterView.getItemAtPosition(i)).getmNombre();
                String idCliente = ((Pedidos) adapterView.getItemAtPosition(i)).getmCliente();
                String ESTADO= ((Pedidos) adapterView.getItemAtPosition(i)).getmEstado();

                editor.putString("IDPEDIDO",idPedido);
                editor.putString("CLIENTE",Cliente);
                editor.putString("ClsSelected",idCliente);
                editor.apply();
                //if (ESTADO.equals("0")) {
                    startActivity(new Intent(BandejaPedidosActivity.this, PedidoActivity.class));
                    finish();
                /*}else if (ESTADO.equals("4")){
                    List<Pedidos> comen = Pedidos_model.getAnulacion(ManagerURI.getDirDb(), BandejaPedidosActivity.this,idPedido);
                    for(Pedidos obj2 : comen) {
                        new Notificaciones().Alert(BandejaPedidosActivity.this,"NOTA DE ANULACION",obj2.getmAnulacion()).show();
                    }
                }else if (ESTADO.equals("3")){
                    List<Pedidos> comen = Pedidos_model.getConfirmacion(ManagerURI.getDirDb(), BandejaPedidosActivity.this,idPedido);
                    for(Pedidos obj2 : comen) {
                        new Notificaciones().Alert(BandejaPedidosActivity.this,"NOTA DE CONFIRMACION",obj2.getmConfirmacion()).show();
                    }
                }*/
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
