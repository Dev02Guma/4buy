package com.example.alderhernandez.a4buy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alderhernandez.a4buy.R;
import com.example.capadatos.Clientes;

import java.util.List;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Clientes_Leads extends ArrayAdapter<Clientes> {
    public Clientes_Leads(Context context, List<Clientes> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_clientes,parent,false);
        }

        TextView nombre = (TextView) convertView.findViewById(R.id.lst_nombre);
        TextView direccion = (TextView) convertView.findViewById(R.id.lst_direccion);
        TextView cedula = (TextView) convertView.findViewById(R.id.lst_cedula);

        Clientes lead = getItem(position);

        nombre.setText(lead.getmNombre());
        direccion.setText(lead.getmDireccion());
        cedula.setText(lead.getmCedula());
        return convertView;
    }
}
