package com.iesribera.tarea2_elena_ortiz.nivel;

import com.iesribera.tarea2_elena_ortiz.Constantes;

public class NivelFacil extends Nivel {

    @Override
    public int getFilas() {
        return Constantes.NivelFacil.FILAS;
    }

    @Override
    public int getColumnas() {
        return Constantes.NivelFacil.COLUMNAS;
    }

    @Override
    public int getHipotenochasOcultas() {
        return Constantes.NivelFacil.HIPOTENOCHAS;
    }


}
