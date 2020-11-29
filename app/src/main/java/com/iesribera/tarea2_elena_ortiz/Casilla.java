package com.iesribera.tarea2_elena_ortiz;

public class Casilla {

	private byte tieneHipotenocha;
	private int hipotenochasAlrededor;


	public Casilla(byte tieneHipotenocha) {
		this.tieneHipotenocha = tieneHipotenocha;
	}

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

	@Override
	public String toString() {
		int mina = 0;
		if (getTieneHipotenocha() == 1) {
			mina = 1;
		}
		return  "[" + mina + "]";
	}

}
