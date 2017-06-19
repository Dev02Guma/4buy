package com.example.alderhernandez.a4buy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.capadatos.Notificaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoActivity2 extends AppCompatActivity {
    private ListView listView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    List<Map<String, Object>> list;
    TextView Total,txtCount;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_dashboard:
                    startActivity(new Intent(PedidoActivity2.this,MainActivity.class));
                    finish();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido2);
        /*BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        listView = (ListView) findViewById(R.id.listaArticulos);
        list = new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        Total = (TextView) findViewById(R.id.Total);
        txtCount= (TextView) findViewById(R.id.txtCountArti);
        setTitle("PEDIDO: "+preferences.getString("NameClsSelected"," --ERROR--"));

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                
                startActivityForResult(new Intent(PedidoActivity2.this,ArticulosActivity.class),0);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity2.this);
                builder.setMessage("¿Desea Eliminar el Articulo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                list.remove(i);
                                Refresh();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create().show();
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity2.this);
                if (list.size()!=0){
                    builder.setMessage("¿Procesar Pedido?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent send = new Intent(PedidoActivity2.this, ResumenActivity.class);
                                    send.putExtra("LIST", (Serializable) list);
                                    startActivity(send);
                                    finish();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).create().show();
                }else{
                    new Notificaciones().Alert(PedidoActivity2.this,"PEDIDO VACIO","INGRESE ARTICULOS AL PEDIDO...").setCancelable(false).setPositiveButton("OK", null).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            Map<String, Object> map = new HashMap<>();
            map.put("ITEMNAME", data.getStringArrayListExtra("myItem").get(0));
            map.put("ITEMCODIGO", data.getStringArrayListExtra("myItem").get(1));
            map.put("PRECIO", data.getStringArrayListExtra("myItem").get(2));
            map.put("ITEMCANTI",  data.getStringArrayListExtra("myItem").get(3));
            map.put("ITEMVALOR",  data.getStringArrayListExtra("myItem").get(4));
            
            list.add(map);
            Refresh();
        }
    }
    public void Refresh(){
        float vLine = 0;
        listView.setAdapter(
                new SimpleAdapter(
                        this,
                        list,
                        R.layout.carrito, new String[] {"ITEMNAME", "ITEMCANTI","ITEMCODIGO","ITEMVALOR","PRECIO" },
                        new int[] {R.id.tvListItemName,R.id.Item_cant,R.id.item_codigo,R.id.Item_valor,R.id.tvListItemPrecio}));
        for (Map<String, Object> obj : list){
            //Log.d("carajo",obj.get("ITEMNAME").toString()+ " "+ obj.get("ITEMVALOR").toString());
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString().replace(",",""));
        }
        Total.setText("TOTAL C$ "+ vLine);
        txtCount.setText(listView.getCount() +" Articulo");

    }

}
