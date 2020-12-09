package com.iesribera.tarea2_elena_ortiz.nivel;


import com.iesribera.tarea2_elena_ortiz.Casilla;

public abstract class Nivel {

	private String nombre;
	private int filas;
	private int columnas;
	private int hipotenochasOcultas;

	abstract public String getNombre();

	abstract public int getFilas();

	abstract public int getColumnas();

	abstract public int getHipotenochasOcultas();

	public static int contarHipotenochasAlrededor(Casilla[][]casillas, int celdaX, int celdaY) {
		int totalHipotenochas = 0;
		for (int i = celdaX - 1; i <= celdaX + 1; i++) {
			for (int j = celdaY - 1; j <= celdaY + 1; j++) {
				try {
					if (casillas[i][j].getTieneHipotenocha() == 1) {
						totalHipotenochas++;
					}
				} catch (ArrayIndexOutOfBoundsException ignored) {
				}
			}
		}
//		casillas[celdaX][celdaY].setHipotenochasAlrededor(totalHipotenochas);
		return totalHipotenochas;
	}

	//TODO: elena
	private void descubrirMinas() {

	}
}
