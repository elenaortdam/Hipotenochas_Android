package com.iesribera.tarea2_elena_ortiz.nivel;


import com.iesribera.tarea2_elena_ortiz.Valores;

public class NivelIntermedio extends Nivel {
	@Override
	public String getNombre() {
		return Valores.NivelMedio.NOMBRE;
	}

	@Override
	public int getFilas() {
		return Valores.NivelMedio.FILAS;
	}

	@Override
	public int getColumnas() {
		return Valores.NivelMedio.COLUMNAS;
	}

	@Override
	public int getHipotenochasOcultas() {
		return Valores.NivelMedio.HIPOTENOCHAS;
	}
}
