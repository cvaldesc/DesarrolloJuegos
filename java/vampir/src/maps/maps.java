package maps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

public class maps{
	private Vector3f edificio1Color = getColorDefault(0.0f,255.0f,64.0f);
	private float red;
	private float green;
	private float blue;
	private Vector3f ColourDetec;
	
	public void mapsGrid(){
		File inputFile = new File("res/map-original.png");
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//System.out.println(w +" "+ h);
		 int w = bufferedImage.getWidth();
         int h = bufferedImage.getHeight();
		    //System.out.println("width, height: " + w + ", " + h);
		 
		    for (int i = 0; i < h; i++) {
		      for (int j = 0; j < w; j++) {
		      //  System.out.println("x,y: " + j + ", " + i);
		        int pixel = bufferedImage.getRGB(j, i);
		        
		        red = (pixel >> 16) & 0xff0000;
		 	    green = (pixel >> 8) & 0xff00;
		 	    blue = (pixel) & 0xff;
		 	    setColourDetec(getPixels(red, green, blue));
		 	   
		 	    GetDetectionObjet(getColourDetec(), edificio1Color);
		      }
		      
		    }
	
	}
 public Vector3f getPixels(float i, float j, float k){
	 return new Vector3f (i,j,k);
 }
 public boolean GetDetectionObjet(Vector3f ColourDetec, Vector3f ColorDefault){
	 if(ColourDetec.equals(ColorDefault)){
		 //System.out.println("true");
		 
		 return true;
		
	 }else{

		 //System.out.println("false");
		 return false;
	 }
		 
 }
public Vector3f getColourDetec() {
	return ColourDetec;
}
public void setColourDetec(Vector3f colourDetec) {
	ColourDetec = colourDetec;
}
public Vector3f getColorDefault(float i, float j, float k) {
	return new Vector3f (i,j,k);
}


	
	
}
