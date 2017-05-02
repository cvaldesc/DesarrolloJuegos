package main;


import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE0_ARB;
import static org.lwjgl.opengl.ARBMultitexture.glActiveTextureARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Character {
	// Class for creating a Character

	int texture2; //char texture
	int texture3; // eyes texture
	String tex2 = "res/gobilnlwjglchar.png";
	String tex3 = "res/gobilnlwjgleyes.png";
	int displaylistch;
	int displaylistchar;
	int displaylistcharj1;
	int displaylistcharj2;
	int displaylistchar6;
	int[] displaylist;
	int[] displaylistside;
	
	LoaderObj obj;
	static String locj1 = "res/lwjglcharactergoblin16.obj";//14
	static String locj2 = "res/lwjglcharactergoblin13.obj";
	static String locg = "res/lwjglcharactergoblin10_000001.obj";
	static String locge = "res/lwjglcharactergoblin12.obj";
	//int vboid2;
	//float[] v;

	int motioncounter = 0;
	int motioncounterback = 20;
	int motionsideright = 0;
	int motionsideleft = 9;

	public Character() {
		// constructor
		loadTexture();
	}

	public void build() {
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		
		//glCallList(displaylistchar);
		
		/*
		if (Main.moving ) {
			for (int i = 0; i < displaylist.length;i++) {
				glCallList(displaylist[i]);
			}
		} else {
			glBindTexture(GL_TEXTURE_2D, texture2);
			glCallList(displaylistchar);
			
			
		}
		*/
		glBindTexture(GL_TEXTURE_2D, texture3);
		glCallList(displaylistchar6);
		glBindTexture(GL_TEXTURE_2D, texture2);
		if (Main.movingforward | Main.movingback | Main.movingsideright | Main.movingsideleft  && !Main.isJump && Main.height == 0) {
			
			if (Main.movingforward)
			glCallList(displaylist[motioncounter]);
			
			if (Main.movingback)
				glCallList(displaylist[motioncounterback]);
			
			if (Main.movingsideright)
				glCallList(displaylistside[motionsideright]);
			
			if (Main.movingsideleft)
				glCallList(displaylistside[motionsideleft]);
			
			/*
		if (motioncounter >= 0 && motioncounter <5) {
		glCallList(displaylistchar); // display list
		motioncounter++;
		} else {
			if (motioncounter >= 5 && motioncounter <10) {
				//glCallList(displaylistchar2);
				//glCallList(displaylist[5]);
				motioncounter++;
			}else {
				if (motioncounter >= 10 && motioncounter <15) {
					//glCallList(displaylistchar3);
					//glCallList(displaylist[10]);
					motioncounter++;
				} else {
					if (motioncounter >= 15 && motioncounter < 20) {
						//glCallList(displaylistchar4);
						//glCallList(displaylist[15]);
						motioncounter++;
					}else {
						//glCallList(displaylistchar5);
						//glCallList(displaylist[19]);
						motioncounter++;
					}
				}
			}
		}
		*/
		}else {
			if (Main.height == 0 ) {
			glCallList(displaylistchar); 
				
			}else {
				if (Main.height >0 & Main.height <3) {
					glCallList(displaylistcharj1);
				}else {
				
				//glCallList(displaylistcharj2);
				glCallList(displaylistcharj2); 
				}
			}
			
		}
		
		if (motioncounter == 20)
			motioncounter = 0;
		
		++motioncounter;
		
		if (motioncounterback == 0)
			motioncounterback = 20;
		
		--motioncounterback;
		
		if (motionsideright == 9)
			motionsideright = 0;
		
		++motionsideright;
		
		if (motionsideleft == 0)
			motionsideleft = 9;
		
		--motionsideleft;
	}

	public void destroy() {
		
		glDeleteTextures(texture2);
		glDeleteTextures(texture3);
		glDeleteLists(displaylistchar6, 1);
		glDeleteLists(displaylistchar, 1);
		glDeleteLists(displaylistcharj1, 1);
		glDeleteLists(displaylistcharj2, 1);
		for (int i = 0;i < displaylist.length;i++) {
			glDeleteLists(displaylist[i], 1);
		}
		for (int i = 0;i < displaylistside.length;i++) {
			glDeleteLists(displaylistside[i], 1);
		}

	}
	
	public void loadFrame(String location) {
		//it loads obj file and put coords together as triangles
		
		Model m = null;
		
		
		try {
			m = LoaderObj.load(new File(location));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		/*
		v = new float[m.vertices.size()*3];
		for(int i = 0; i<(v.length/3) ; i+=3) {
			v[i] = m.vertices.get(i).x;
			v[i+1] = m.vertices.get(i).y;
			v[i+2] = m.vertices.get(i).z;
		}
		*/
		
		glBegin(GL_TRIANGLES);
		for (Face face : m.faces) {
			Vector2f t1 = m.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glTexCoord2f(t1.x, 1 - t1.y);
			Vector3f n1 = m.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m.textures.get((int) face.textures.y - 1);
			glTexCoord2f(t2.x, 1 - t2.y);
			Vector3f n2 = m.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m.textures.get((int) face.textures.z - 1);
			glTexCoord2f(t3.x, 1 - t3.y);
			Vector3f n3 = m.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();

		
		
		
	}

	public void createCharacter() {
		
		
		//load stand frame
		displaylistchar = glGenLists(1);
		glNewList(displaylistchar, GL_COMPILE);
		loadFrame(locg);
		glEndList();
		
		//load eye frame
		displaylistchar6 = glGenLists(1);
		glNewList(displaylistchar6, GL_COMPILE);
		loadFrame(locge);
		glEndList();
		
		//load two frames for jump
		displaylistcharj2 = glGenLists(1);
		glNewList(displaylistcharj2, GL_COMPILE);
		loadFrame(locj2);
		glEndList();
		
		displaylistcharj1 = glGenLists(1);
		glNewList(displaylistcharj1, GL_COMPILE);
		loadFrame(locj1);
		glEndList();
		
		
		//load frames for walking sideways
		displaylistside = new int[10];
		displaylistside[0] = glGenLists(1);
		glNewList(displaylistside[0], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin17_000001.obj");
		glEndList();
		displaylistside[1] = displaylistside[0];
		displaylistside[2] = glGenLists(1);
		glNewList(displaylistside[2], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin17_000002.obj");
		glEndList();
		displaylistside[3] = displaylistside[2];
		displaylistside[4] = glGenLists(1);
		glNewList(displaylistside[4], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin17_000003.obj");
		glEndList();
		displaylistside[5] = displaylistside[4];
		displaylistside[6] = glGenLists(1);
		glNewList(displaylistside[6], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin17_000004.obj");
		glEndList();
		displaylistside[7] = displaylistside[6];
		displaylistside[8] = displaylistside[0];
		displaylistside[9] = displaylistside[8];
		
		//load frames for walk
		displaylist = new int[21];
		displaylist[0] = glGenLists(1);
		glNewList(displaylist[0], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000005.obj");
		glEndList();
		displaylist[1] = glGenLists(1);
		glNewList(displaylist[1], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000006.obj");
		glEndList();
		displaylist[2] = glGenLists(1);
		glNewList(displaylist[2], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000007.obj");
		glEndList();
		displaylist[3] = glGenLists(1);
		glNewList(displaylist[3], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000008.obj");
		glEndList();
		displaylist[4] = glGenLists(1);
		glNewList(displaylist[4], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000009.obj");
		glEndList();
		displaylist[5] = glGenLists(1);
		glNewList(displaylist[5], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000010.obj");
		glEndList();
		displaylist[6] = glGenLists(1);
		glNewList(displaylist[6], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000011.obj");
		glEndList();
		displaylist[7] = glGenLists(1);
		glNewList(displaylist[7], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000012.obj");
		glEndList();
		displaylist[8] = glGenLists(1);
		glNewList(displaylist[8], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000013.obj");
		glEndList();
		displaylist[9] = glGenLists(1);
		glNewList(displaylist[9], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000014.obj");
		glEndList();
		displaylist[10] = glGenLists(1);
		glNewList(displaylist[10], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000015.obj");
		glEndList();
		displaylist[11] = glGenLists(1);
		glNewList(displaylist[11], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000016.obj");
		glEndList();
		displaylist[12] = glGenLists(1);
		glNewList(displaylist[12], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000017.obj");
		glEndList();
		displaylist[13] = glGenLists(1);
		glNewList(displaylist[13], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000018.obj");
		glEndList();
		displaylist[14] = glGenLists(1);
		glNewList(displaylist[14], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000019.obj");
		glEndList();
		displaylist[15] = glGenLists(1);
		glNewList(displaylist[15], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000020.obj");
		glEndList();
		displaylist[16] = glGenLists(1);
		glNewList(displaylist[16], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000021.obj");
		glEndList();
		displaylist[17] = glGenLists(1);
		glNewList(displaylist[17], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000022.obj");
		glEndList();
		displaylist[18] = glGenLists(1);
		glNewList(displaylist[18], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000023.obj");
		glEndList();
		displaylist[19] = glGenLists(1);
		glNewList(displaylist[19], GL_COMPILE);
		loadFrame("res/lwjglcharactergoblin11_000024.obj");
		glEndList();
		displaylist[20] = displaylist[0];

	}

	public void loadTexture() {
		// loader for textures 
		
		BufferedImage image2 = TextureLoader.loadImage(tex2);
		texture2 = TextureLoader.loadTexture(image2);
		
		BufferedImage image3 = TextureLoader.loadImage(tex3);
		texture3 = TextureLoader.loadTexture(image3);

	}
	


}
