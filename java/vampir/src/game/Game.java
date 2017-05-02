package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;





import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRender;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;


public class Game {

	public static void main(String[] args){
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//*******Terrain texture stuff*****
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
      
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
	
		
		ModelTexture fernTextureAtrlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtrlas.setNumberOfRows(2);
		
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader),new ModelTexture(loader.loadTexture("lowPolyTree")));
       /* TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.loadTexture("grassTexture")));*/
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),fernTextureAtrlas);
        TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),new ModelTexture(loader.loadTexture("lamp")));
        
       /* grass.getTexture().setHasTransparenty(true);
        grass.getTexture().setUseFakeLighting(true);*/
        fern.getTexture().setHasTransparenty(true);
		fern.getTexture().setUseFakeLighting(true);
		
		 Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap"); 
		 
	      
        List<Entity> entities = new ArrayList<Entity>();
        
        Random random = new Random();
        
        for(int i = 0; i< 500; i++){
        	if(i % 2 ==0){
        		float x= random.nextFloat() * 800;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(fern, random.nextInt(4),new Vector3f(x,y,z),0,random.nextFloat()*360,0, 0.3f) );
        		/*x = random.nextFloat() * 800;
            	z = random.nextFloat() * -600;
            	y = terrain.getHeightOfTerrain(x, z);
            	entities.add(new Entity(grass,new Vector3f(x,y,z),0,0,0, 0.6f) );*/
        		
        	}
        	if(i% 5 == 0){
        		float x= random.nextFloat() * 800;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(tree, new Vector3f(x,y,z),0,0,0, random.nextFloat()*1+4) );
            	
        	}
        	
        	
        	
        	
        }
        
        
      
       
        
		
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f,0.002f)));
       
        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
        
        
        
        
       	MasterRender renderer = new MasterRender(loader);
		
       	RawModel person = OBJLoader.loadObjModel("person", loader);
       	TexturedModel stanforperson = new TexturedModel(person, new ModelTexture(loader.loadTexture("playerTexture")));
       	
       	
       	
       	Player player = new Player(stanforperson, new Vector3f(799, 5, -274 ), 0, 100, 0, 0.7f);
       	Camera camera = new Camera(player);
		while(!Display.isCloseRequested()){
			player.move(terrain);
			camera.move();
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			 
			
			
			
			for (Entity entitie:entities){
                renderer.processEntity(entitie);
			}
			
			renderer.render(lights, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}


	

}
