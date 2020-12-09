package com.iesribera.tarea2_elena_ortiz.nivel;


import com.iesribera.tarea2_elena_ortiz.Constantes;

public class NivelAvanzado extends Nivel {
	@Override public String getNombre() {
		return Constantes.NivelAvanzado.NOMBRE;
	}

	@Override public int getFilas() {
		return Constantes.NivelAvanzado.FILAS;
	}

	@Override public int getColumnas() {
		return Constantes.NivelAvanzado.COLUMNAS;
	}

	@Override public int getHipotenochasOcultas() {
		return Constantes.NivelAvanzado.HIPOTENOCHAS;
	}
}
