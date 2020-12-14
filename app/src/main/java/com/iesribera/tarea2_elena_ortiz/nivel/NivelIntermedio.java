package com.iesribera.tarea2_elena_ortiz.nivel;


import com.iesribera.tarea2_elena_ortiz.Constantes;

public class NivelIntermedio extends Nivel {

    @Override
	public int getFilas() {
        return Constantes.NivelMedio.FILAS;
    }

	@Override
	public int getColumnas() {
        return Constantes.NivelMedio.COLUMNAS;
    }

	@Override
	public int getHipotenochasOcultas() {
        return Constantes.NivelMedio.HIPOTENOCHAS;
    }
}
