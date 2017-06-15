package com.example.alderhernandez.a4buy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capadatos.ManagerURI;
import com.example.capadatos.Pedidos;
import com.example.capadatos.Pedidos_model;
import com.example.capadatos.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ResumenActivity extends AppCompatActivity {
    TextView lblNombreClliente;
    TextView lblNombreVendedor;
    TextView countArti;
    TextView Total;
    TextView Atendio;
    TextView txtidPedido;

    private static ListView listView;
    float vLine = 0;
    ArrayList<Pedidos> mPedido = new ArrayList<>();
    ArrayList<Pedidos> mDetallePedido = new ArrayList<>();
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    String CodCls,idPedido,bandera = "0",comentario;

    TextView textView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        setTitle("RESUMEN");
        Intent ints = getIntent();
        listView = (ListView) findViewById(R.id.ListView1);

        final List<Map<String, Object>> list = (List<Map<String, Object>>) ints.getSerializableExtra("LIST");
        listView.setAdapter(
                new SimpleAdapter(this, list,R.layout.list_item_resumen,
                new String[] {"ITEMNAME", "ITEMCANTI","ITEMCODIGO","ITEMVALOR","PRECIO" },
                new int[] { R.id.tvListItemName,R.id.Item_cant,R.id.Item_descr,R.id.Item_valor,R.id.tvListItemPrecio })
        );
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        CodCls =  preferences.getString("ClsSelected","");
        Total = (TextView) findViewById(R.id.Total);
        Atendio = (TextView) findViewById(R.id.NombreVendedor);
        txtidPedido = (TextView) findViewById(R.id.IdPedido);

        lblNombreClliente = (TextView) findViewById(R.id.NombreCliente);
        lblNombreVendedor = (TextView) findViewById(R.id.NombreVendedor);
        lblNombreVendedor.setText("");
        lblNombreClliente.setText(ints.getStringExtra("NombreCliente"));
        countArti = (TextView) findViewById(R.id.txtCountArti);

        idPedido = preferences.getString("IDPEDIDO", "");

        if (!idPedido.equals("")){

            Atendio.setText("LE ATENDIO: "+preferences.getString("VENDEDOR",""));
            txtidPedido.setText(idPedido);
            bandera = "1";
            timer.cancel();
            LinearLayout mainLayout=(LinearLayout)findViewById(R.id.clockLayout);
            mainLayout.setVisibility(View.GONE);
        }else{


            idPedido = "PEDIDO-" + "P1234";
            txtidPedido.setText(idPedido);
            Atendio.setText("LE ATENDIO: VENDEDOR 1");
        }
        for (Map<String, Object> obj : list){
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString().replace(",",""));
        }
        Total.setText("TOTAL C$ "+ String.valueOf(vLine));
        countArti.setText(listView.getCount() +" Articulo");
        findViewById(R.id.btnSaveSale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResumenActivity.this)
                        .setTitle("CONFIRMACION")
                        .setMessage("¿DESEA GUARDAR EL PEDIDO?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CodCls!="") {
                                        //Toast.makeText(ResumenActivity.this, "GUARDANDO CASO: "+bandera, Toast.LENGTH_SHORT).show();
                                        guardar(list);
                                }else {
                                    Toast.makeText(ResumenActivity.this, "ERROR AL GUARDAR PEDIDO, INTENTELO MAS TARDE", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("NO",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

    }

    public void guardar(List<Map<String, Object>> list){
        Float total;
        Total = (TextView) findViewById(R.id.Total);
        total = Float.parseFloat(Total.getText().toString().replace("TOTAL C$ ",""));

            int key = SQLiteHelper.getId(ManagerURI.getDirDb(), ResumenActivity.this, "PEDIDOS");
            idPedido = "PEDIDO-" + "P1234";
            Float nTotal = 0.0f;
            for (Map<String, Object> obj : list) {
                nTotal += Float.parseFloat(obj.get("ITEMVALOR").toString().replace(",",""));
            }
            Pedidos tmp = new Pedidos();
            tmp.setmIdPedido(idPedido);
            tmp.setmVendedor(preferences.getString("VENDEDOR", "00"));
            tmp.setmCliente(CodCls);
            tmp.setmNombre(preferences.getString("NameClsSelected", " CLIENTE NO ENCONTRADO"));
            tmp.setmFecha("2017-06-30");
            tmp.setmPrecio(String.valueOf(nTotal));
            tmp.setmEstado("0");
            mPedido.add(tmp);

            Pedidos_model.SavePedido(ResumenActivity.this, mPedido);
            for (Map<String, Object> obj2 : list) {
                Pedidos tmpDetalle = new Pedidos();
                tmpDetalle.setmIdPedido(idPedido);
                tmpDetalle.setmArticulo(obj2.get("ITEMCODIGO").toString());
                tmpDetalle.setmDescripcion(obj2.get("ITEMNAME").toString());
                tmpDetalle.setmCantidad(obj2.get("ITEMCANTI").toString());
                tmpDetalle.setmPrecio(obj2.get("ITEMVALOR").toString());

                mDetallePedido.add(tmpDetalle);

            }
            Pedidos_model.SaveDetallePedido(ResumenActivity.this, mDetallePedido);
            startActivity(new Intent(ResumenActivity.this,MainActivity.class));

            finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ResumenActivity.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}