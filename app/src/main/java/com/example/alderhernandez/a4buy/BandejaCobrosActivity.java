package com.example.alderhernandez.a4buy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alderhernandez.a4buy.Adapters.Cobros_Leads;
import com.example.capadatos.Cobros;
import com.example.capadatos.Cobros_model;
import com.example.capadatos.ManagerURI;

import java.util.ArrayList;
import java.util.List;

public class BandejaCobrosActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Cobros> fList;
    TextView mTotal;
    float mCalTotal;

    TextView mCliente,mImporte,mFecha,mObservaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_cobros);

        setTitle("COBROS DEL DIA");
        listView = (ListView) findViewById(R.id.listView_cobros);
        fList = new ArrayList<>();
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        mTotal = (TextView) findViewById(R.id.txtTotalCobros);

        List<Cobros> lstObj = Cobros_model.getCobros(ManagerURI.getDirDb(), BandejaCobrosActivity.this,false);

        if (lstObj.size() == 0){
            findViewById(R.id.imgMoneyOff) .setVisibility(View.VISIBLE);
            findViewById(R.id.txtMoneyOff) .setVisibility(View.VISIBLE);
        }

        for(Cobros obj : lstObj) {
            fList.add(obj);
            mCalTotal += Float.parseFloat(obj.getmImporte());
        }
        mTotal.setText("C$ " + String.valueOf(mCalTotal));
        listView.setAdapter(new Cobros_Leads(this, fList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater li = LayoutInflater.from(BandejaCobrosActivity.this);
                final Cobros crbs = (Cobros) adapterView.getItemAtPosition(i);
                View promptsView = li.inflate(R.layout.info_cobro, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BandejaCobrosActivity.this);
                alertDialogBuilder.setView(promptsView);

                mCliente = (TextView) promptsView.findViewById(R.id.txtFrmCliente);
                mImporte = (TextView) promptsView.findViewById(R.id.txtFrmImporte);
                mFecha = (TextView) promptsView.findViewById(R.id.txtFrmFecha);
                mObservaciones = (TextView) promptsView.findViewById(R.id.txtFrmObservaciones);

                mCliente.setText(crbs.getmCliente());
                mImporte.setText(crbs.getmImporte());
                mFecha.setText(crbs.getmFecha());
                mObservaciones.setText(crbs.getmObservacion());


                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).create().show();
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
