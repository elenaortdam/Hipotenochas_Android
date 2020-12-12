package com.iesribera.tarea2_elena_ortiz;

import com.iesribera.tarea2_elena_ortiz.nivel.Nivel;

import java.util.Random;

public class Tablero {

    private final int[][] casillas;
//    private final Casilla[][] casillas;
/*
    public Tablero(Casilla[][] casillas) {
        this.casillas = casillas;
    }

 */

    public Tablero(int[][] casillas) {
        this.casillas = casillas;
    }

    public Tablero(Nivel nivel) {
        casillas = crearTablero(nivel).getCasillas();
    }

    public int[][] getCasillas() {
        return casillas;
    }

    public Tablero crearTablero(Nivel nivel) {
        int[][] casillas = new int[nivel.getColumnas()][nivel.getFilas()];
        int hipotenochasTotales = 0;
        for (int i = 0; i < nivel.getColumnas(); i++) {
            int maxHipotenochasFila = nivel.getHipotenochasOcultas() / nivel.getFilas();
            int hipotenochasCreadas = 0;
            int numeroRandom = getRandomNumber(0, nivel.getColumnas() - 1);
            for (int j = 0; j < nivel.getFilas(); j++) {
                casillas[i][j] = 0;
                if (hipotenochasTotales < nivel.getHipotenochasOcultas()
                        && hipotenochasCreadas < maxHipotenochasFila
                        && j == numeroRandom) {
                    casillas[i][j] = 1;
                    hipotenochasTotales++;
                    hipotenochasCreadas++;
                } else {
                    casillas[i][j] = 0;
                }

            }
        }
        Tablero tablero = new Tablero(casillas);

        while (hipotenochasTotales < nivel.getHipotenochasOcultas()) {
            asignarHipotenochas(tablero.getCasillas(), getRandomNumber(0, nivel.getFilas() - 1),
                    getRandomNumber(0, nivel.getColumnas() - 1));
            hipotenochasTotales++;
        }

        return tablero;
    }

    private void asignarHipotenochas(int[][] casillas, int i, int j) {
        if (casillas[i][j] == 0) {
            casillas[i][j] = 1;
        } else {
            try {
                asignarHipotenochas(casillas, i, j + 1);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                asignarHipotenochas(casillas, i, 0);
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
