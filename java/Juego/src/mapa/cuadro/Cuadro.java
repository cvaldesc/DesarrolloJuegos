package mapa.cuadro;

import graficos.Pantalla;
import graficos.Sprite;

public abstract class Cuadro {
//Tiles
	public int x;
	public int y;
	
	public Sprite sprite;
	public static final int LADO = 16;
	
	//coleccion de cuadros
	public static final Cuadro VACIO = new CuadroVacio(Sprite.VACIO);
	public static final Cuadro ASFALTO = new CuadroAsfalto(Sprite.ASFALTO);
	//fin coleccion de cuadros
	
	
	public Cuadro(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void mostrar(int x, int y, Pantalla pantalla){
		pantalla.mostrarCuadro(x << 3 , y << 3, this);
	}
	public boolean solido(){
		return false;
	}
}
