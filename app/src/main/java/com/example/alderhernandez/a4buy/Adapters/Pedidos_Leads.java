package com.example.alderhernandez.a4buy.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alderhernandez.a4buy.R;
import com.example.capadatos.Pedidos;

import java.util.List;


/**
 * Created by alder.hernandez on 22/03/2017.
 */

public class Pedidos_Leads extends ArrayAdapter<Pedidos> {
    private AssetManager assetMgr;
    public Pedidos_Leads(Context context, List<Pedidos> objects) {
        super(context, 0, objects);
        assetMgr = context.getResources().getAssets();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_pedidos,parent,false);
        }

        TextView midPedido = (TextView) convertView.findViewById(R.id.txt_id_pedido);
        TextView mCliente = (TextView) convertView.findViewById(R.id.lst_cliente);
        TextView mFecha = (TextView) convertView.findViewById(R.id.lst_fecha);
        TextView mMonto = (TextView) convertView.findViewById(R.id.lst_total);
        TextView mEstado = (TextView) convertView.findViewById(R.id.txt_id_estado);

        Pedidos lead = getItem(position);

        mFecha.setText(lead.getmFecha());
        midPedido.setText(lead.getmIdPedido());
        mCliente.setText(lead.getmCliente()+" "+lead.getmNombre());
        mMonto.setText("C$ " + Float.parseFloat(lead.getmPrecio()));

        ImageView img= (ImageView)convertView.findViewById(R.id.img);
        Integer estado = Integer.valueOf(lead.getmEstado());


        return convertView;
    }
}
