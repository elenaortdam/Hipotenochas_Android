package com.iesribera.tarea2_elena_ortiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Personaje extends DialogFragment implements AdapterView.OnItemSelectedListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View va = inflater.inflate(R.layout.personaje, null);
        builder.setView(va)
                .setPositiveButton(R.string.volver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AdaptadorPersonalizado adaptadorSeleccion = new AdaptadorPersonalizado(getActivity(),
                R.layout.personaje, getResources().getStringArray(R.array.personaje));
        Spinner spa = va.findViewById(R.id.spinner_personaje);
        spa.setOnItemSelectedListener(this);
        spa.setAdapter(adaptadorSeleccion);
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.setPersonaje(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class AdaptadorPersonalizado extends ArrayAdapter<String> {

        public AdaptadorPersonalizado(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View fila = inflater.inflate(R.layout.sl_incog, parent, false);
            TextView txt = fila.findViewById(R.id.personaje);
            String[] arrayNombres = getResources().getStringArray(R.array.personaje);
            TypedArray arrayImagenes = getResources().obtainTypedArray(R.array.imagenes);
            txt.setText(arrayNombres[position]);
            txt.setCompoundDrawablesWithIntrinsicBounds(arrayImagenes.getDrawable(position), null, null, null);
            return fila;
        }
    }

}
