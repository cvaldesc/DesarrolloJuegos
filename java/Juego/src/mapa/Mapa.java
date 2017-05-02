package mapa;

import mapa.cuadro.Cuadro;
import graficos.Pantalla;

public abstract class Mapa {
	protected int ancho;
	protected int alto;
	
	protected int[] cuadros;
	
	
	//nivel aleatorio
	public Mapa(int ancho, int alto){
		this.ancho = ancho;
		this.alto= alto;
		
		cuadros = new int[ancho * alto];
		generarMapa();
	}
	//nivel generado
	public Mapa(String ruta){
		cargarMapa(ruta);
		generarMapa();
	}
	
	private void cargarMapa(String ruta) {
		
		
	}
	protected void generarMapa() {
		
		
	}
	public void actualizar(){
		
	}
	// traducir movimiento de los tiles a los movimientos de los pixeles
	public void mostrar(int compensacionX, int compensacionY, Pantalla pantalla){
		
		pantalla.establecerDiferencia(compensacionX, compensacionY);
		//Temporal
		
		// lado de pantalla oeste
		int o = compensacionX >> 3;
		// lado de pantalla este 
		int e = (compensacionX + pantalla.obtenAncho() +Cuadro.LADO ) >> 3;
		// lado de pantalla norte
		int n = compensacionY >> 3;
		// lado de pantalla sur
		int s = (compensacionY + pantalla.obtenAlto()+ Cuadro.LADO) >> 3;
		//FIn TEMPORAL
		
		for(int y =n; y < s; y++){
			for(int x =o;x< e; x++){
				obtenerCuadro(x, y).mostrar(x, y, pantalla);
			}
		}
	}
	public Cuadro obtenerCuadro(final int x, final int y){
		if(x < 0 || y < 0 || x >= ancho || y >= alto){
			return Cuadro.VACIO;
		}
		switch( cuadros[x + y * ancho]){
			case 0:
				 return Cuadro.ASFALTO;
				
			default:
				 return Cuadro.VACIO;
					
		}
	
		
	}
	
	
	
}
