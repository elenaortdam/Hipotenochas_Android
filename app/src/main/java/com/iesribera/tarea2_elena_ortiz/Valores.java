package com.iesribera.tarea2_elena_ortiz;

public final class Valores {
	private Valores() {
	}

	public static final class NivelFacil {
		public static final int OPCION_MENU = 0;
		public static final String NOMBRE = "FACIL";
		public static final String TEXTO = "Nivel Fácil";
		public static final int HIPOTENOCHAS = 10;
		public static final int COLUMNAS = 8;
		public static final int FILAS = COLUMNAS;
	}

	public static final class NivelMedio {
		public static final int OPCION_MENU = 1;
		public static final String NOMBRE = "INTERMEDIO";
		public static final String TEXTO = "Nivel Intermedio";
		public static final int HIPOTENOCHAS = 30;
		public static final int COLUMNAS = 12;
		public static final int FILAS = COLUMNAS;
	}

	public static final class NivelAvanzado {
		public static final int OPCION_MENU = 2;
		public static final String NOMBRE = "AVANZADO";
		public static final String TEXTO = "Nivel Avanzado";
		public static final int HIPOTENOCHAS = 60;
		public static final int COLUMNAS = 16;
		public static final int FILAS = COLUMNAS;
	}
}
