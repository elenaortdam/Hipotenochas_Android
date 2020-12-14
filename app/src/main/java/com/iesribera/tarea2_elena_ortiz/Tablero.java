package com.iesribera.tarea2_elena_ortiz;

import com.iesribera.tarea2_elena_ortiz.nivel.Nivel;

import java.util.Random;

public class Tablero {

    private final int[][] casillas;

    public Tablero(int[][] casillas) {
        this.casillas = casillas;
    }

    public Tablero(Nivel nivel) {
        casillas = crearTablero(nivel).getCasillas();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {

                int valor = casillas[i][j];
                if (valor != Constantes.TIENE_HIPOTENOCHA) {
                    casillas[i][j] = contarHipotenochasAlrededor(casillas, i, j);
                }
            }
        }
    }

    public static int contarHipotenochasAlrededor(int[][] casillas, int celdaX, int celdaY) {
        int totalHipotenochas = 0;
        for (int i = celdaX - 1; i <= celdaX + 1; i++) {
            for (int j = celdaY - 1; j <= celdaY + 1; j++) {
                try {
                    if (casillas[i][j] == Constantes.TIENE_HIPOTENOCHA) {
                        totalHipotenochas++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }
        return totalHipotenochas;
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
                    casillas[i][j] = Constantes.TIENE_HIPOTENOCHA;
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
            casillas[i][j] = Constantes.TIENE_HIPOTENOCHA;
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
