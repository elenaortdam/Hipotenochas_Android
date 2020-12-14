package com.iesribera.tarea2_elena_ortiz.nivel;


import com.iesribera.tarea2_elena_ortiz.Casilla;
import com.iesribera.tarea2_elena_ortiz.Constantes;

public abstract class Nivel {

	private int filas;
	private int columnas;
	private int hipotenochasOcultas;


	abstract public int getFilas();

	abstract public int getColumnas();

	abstract public int getHipotenochasOcultas();

	public static int contarHipotenochasAlrededor(Casilla[][]casillas, int celdaX, int celdaY) {
		int totalHipotenochas = 0;
		for (int i = celdaX - 1; i <= celdaX + 1; i++) {
			for (int j = celdaY - 1; j <= celdaY + 1; j++) {
				try {
					if (casillas[i][j].getTieneHipotenocha() == Constantes.TIENE_HIPOTENOCHA) {
						totalHipotenochas++;
					}
				} catch (ArrayIndexOutOfBoundsException ignored) {
				}
			}
		}
		return totalHipotenochas;
	}

}
