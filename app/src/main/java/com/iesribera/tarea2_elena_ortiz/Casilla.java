package com.iesribera.tarea2_elena_ortiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class Casilla extends Button {

	private final int fila;
	private final int columna;
	private final byte tieneHipotenocha;


	public boolean isPulsada() {
		return pulsada;
	}

	public void setPulsada(boolean pulsada) {
		this.pulsada = pulsada;
	}

	private boolean pulsada = false;


	public Casilla(Context context, int columna, int fila, byte tieneHipotenocha) {
		super(context);
		this.fila = fila;
		this.columna = columna;
		this.tieneHipotenocha = tieneHipotenocha;
	}

	public byte getTieneHipotenocha() {
		return tieneHipotenocha;
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

}
