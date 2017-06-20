package com.jgaleano.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josimargaleanoflorez on 19/06/17.
 */

public class AdapterNotificacion extends BaseAdapter {
    protected Activity activity;
    protected List<Notificacion> items;

    public AdapterNotificacion (Activity activity, List<Notificacion> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(List<Notificacion> notificacion) {
        for (int i = 0; i < notificacion.size(); i++) {
            items.add(notificacion.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_notificacion, null);
        }

        Notificacion dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.titulo);
        title.setText(dir.getTitle());

        TextView description = (TextView) v.findViewById(R.id.subtitulo);
        description.setText(dir.getDescription());

        ImageView imagen = (ImageView) v.findViewById(R.id.imagen);
        imagen.setImageDrawable(dir.getImage());

        return v;
    }
}
