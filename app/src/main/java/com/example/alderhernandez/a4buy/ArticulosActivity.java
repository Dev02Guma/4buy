package com.example.alderhernandez.a4buy;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alderhernandez.a4buy.Adapters.Articulo_Leads;
import com.example.capadatos.Articulo;
import com.example.capadatos.Articulos_model;
import com.example.capadatos.ManagerURI;
import com.example.capadatos.Notificaciones;

import java.util.ArrayList;
import java.util.List;

public class ArticulosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    private ListView listView;
    EditText Inputcant,InputExiste,InputPrecio;
    RadioButton radioButton;
    Spinner spinner;
    Float vLinea,SubTotalLinea,TotalFinalLinea;
    private SearchView searchView;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private Articulo_Leads lbs;
    private List<Articulo> objects;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        listView = (ListView) findViewById(R.id.listView);
        ReferenciasContexto.setContextArticulo(ArticulosActivity.this);

        objects = Articulos_model.getArticulos(ManagerURI.getDirDb(), ReferenciasContexto.getContextArticulo());

        lbs = new Articulo_Leads(this, objects);
        listView.setAdapter(lbs);
        final ArrayList<String> strings = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater li = LayoutInflater.from(ArticulosActivity.this);
                View promptsView = li.inflate(R.layout.input_cant, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArticulosActivity.this);
                alertDialogBuilder.setView(promptsView);
                final Articulo datos = (Articulo) parent.getItemAtPosition(position);
                Inputcant = (EditText) promptsView.findViewById(R.id.txtFrmCantidad);
                InputPrecio = (EditText) promptsView.findViewById(R.id.txtFrmPrecio);
                InputExiste = (EditText) promptsView.findViewById(R.id.txtFrmExistencia);

                InputPrecio.setText(datos.getmPrecio());
                InputExiste.setText(datos.getmExistencia());
                InputPrecio.setEnabled(false);
                InputExiste.setEnabled(false);
                alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        // Prevent dialog close on back press button
                        return keyCode == KeyEvent.KEYCODE_BACK;
                    }
                });
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Float existen = Float.parseFloat(datos.getmExistencia());
                                Float cantidad = Float.parseFloat(Inputcant.getText().toString());
                                if (existen>0 && cantidad>0){
                                    if (existen >= cantidad) {
                                        Float total = Float.parseFloat(datos.getmPrecio()) * cantidad;
                                        strings.add(datos.getmName());
                                        strings.add(datos.getmCodigo());
                                        strings.add(datos.getmPrecio());
                                        strings.add(cantidad.toString());
                                        strings.add(total.toString());
                                        getIntent().putStringArrayListExtra("myItem", strings);
                                        setResult(RESULT_OK, getIntent());
                                        finish();
                                    }else{
                                        new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "LA EXISTENCIA ACTUAL ES: (" +datos.getmExistencia()+")")
                                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        }).show();
                                    }
                                }else{
                                    new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "ARTICULO SIN EXISTENCIA, FAVOR ACTUALICE")
                                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).show();
                                }
                            }
                }).setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            return true;
        }
        return  false;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //searchItem = menu.findItem(R.id.action_search);
        /*searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();*/
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            editor.putBoolean("menu", false).apply();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterData(newText);
        return false;
    }
    public void filterData(String query) {
        /*query = query.toLowerCase(Locale.getDefault());
        ArrayList<Articulo> newList = new ArrayList<>();
        if (query.isEmpty()){
            for(Articulo articulo:objects){
                newList.add(articulo);
            }
        }else{
            //ArrayList<Articulo> newList = new ArrayList<>();
            for(Articulo articulo:objects){
                if (articulo.getmName().toLowerCase().contains(query)){
                    newList.add(articulo);
                }
            }
            //listView.setAdapter(new Articulo_Leads(this, newList));
        }
        listView.setAdapter(new Articulo_Leads(this, newList));*/
    }
}
