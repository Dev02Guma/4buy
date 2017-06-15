package com.example.alderhernandez.a4buy.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alderhernandez.a4buy.R;
import com.example.capadatos.Articulo;

import java.util.List;


public class Articulo_Leads extends ArrayAdapter<Articulo> {
    private AssetManager assetMgr;
    public Articulo_Leads(Context context, List<Articulo> objects) {
        super(context, 0, objects);
        assetMgr = context.getResources().getAssets();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item_articulos,parent,false);
        }
        TextView nombre = (TextView) convertView.findViewById(R.id.lst_nombre);
        TextView precio = (TextView) convertView.findViewById(R.id.lst_precio);
        TextView existencia = (TextView) convertView.findViewById(R.id.lst_existencia);
        TextView categoria = (TextView) convertView.findViewById(R.id.lst_categoria);


        Articulo lead = getItem(position);

        nombre.setText(lead.getmName());
        precio.setText("C$ "+lead.getmPrecio());
        existencia.setText("EXIST: "+lead.getmExistencia());
        categoria.setText(" ["+lead.getmCategoria()+"]");

        return convertView;
    }
}
