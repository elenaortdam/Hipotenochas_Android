package com.iesribera.tarea2_elena_ortiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class Casilla extends Button {

	private int fila;
	private int columna;
	private byte tieneHipotenocha;
	private int hipotenochasAlrededor;


	public Casilla(Context context, int columna, int fila, byte tieneHipotenocha) {
		super(context);
		this.fila = fila;
		this.columna = columna;
		this.tieneHipotenocha = tieneHipotenocha;
	}

//	public Casilla(byte tieneHipotenocha) {
//		this.tieneHipotenocha = tieneHipotenocha;
//	}

	public byte getTieneHipotenocha() {
		return tieneHipotenocha;
	}

	public void setHipotenocha(byte tieneHipotenocha) {
		this.tieneHipotenocha = tieneHipotenocha;
	}

	public int getHipotenochasAlrededor() {
		return hipotenochasAlrededor;
	}

	public void setHipotenochasAlrededor(int minasAlrededor) {
		this.hipotenochasAlrededor = minasAlrededor;
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	@Override
	public String toString() {
		int mina = 0;
		if (getTieneHipotenocha() == 1) {
			mina = 1;
		}
		return "[" + mina + "]";
	}

}
