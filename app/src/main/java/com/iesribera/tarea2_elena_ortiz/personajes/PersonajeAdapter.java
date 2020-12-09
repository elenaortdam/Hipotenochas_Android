package com.iesribera.tarea2_elena_ortiz.personajes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iesribera.tarea2_elena_ortiz.R;

import java.util.ArrayList;

public class PersonajeAdapter extends BaseAdapter {
    private ArrayList<Personaje> personajes;
    private Context contexto;

    public PersonajeAdapter(Context context, ArrayList<Personaje> personajes) {
        setContexto(context);
        setPersonajes(personajes);
    }

    @Override
    public int getCount() {
        return getPersonajes().size();
    }

    @Override
    public Object getItem(int i) {
        return getPersonajes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = view;

        if (item == null) {
            LayoutInflater inflater = (LayoutInflater) getContexto().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.personaje, null);
        }

        TextView nombre = item.findViewById(R.id.personaje);
        nombre.setText(personajes.get(i).getNombre());

        ImageView imagen = item.findViewById(R.id.imagenPersonaje);
        imagen.setImageDrawable(personajes.get(i).getImagen());

        return item;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }
}
