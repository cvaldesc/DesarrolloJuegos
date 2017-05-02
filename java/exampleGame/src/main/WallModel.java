package main;

import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE0_ARB;
import static org.lwjgl.opengl.ARBMultitexture.glMultiTexCoord2fARB;

import static org.lwjgl.opengl.ARBMultitexture.glActiveTextureARB;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class WallModel {

	LoaderObj obj;
	static String loc = "res/lwjglminus1floor2.obj";
	int displaylist;
	static String loc2 = "res/lwjglfloor.obj";
	int displaylist2;
	static String loc3 = "res/lwjglfloorwindow.obj";
	int displaylist3;
	static String loc4 = "res/lwjglwindow.obj";
	int displaylist4;
	static String loc5 = "res/lwjglfloorwindow2.obj";
	int displaylist5;
	int texture;
	int texture2;
	int texture3;
	
	public WallModel() {
		
		displaylist = glGenLists(1);
		glNewList(displaylist, GL_COMPILE);

		Model m = null;
		
		
		try {
			m = LoaderObj.load(new File(loc));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		glBegin(GL_TRIANGLES);
		for (Face face : m.faces) {
			Vector2f t1 = m.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glTexCoord2f(t1.x*20, 1 - t1.y*20);
			Vector3f n1 = m.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m.textures.get((int) face.textures.y - 1);
			glTexCoord2f(t2.x*20, 1 - t2.y*20);
			Vector3f n2 = m.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m.textures.get((int) face.textures.z - 1);
			glTexCoord2f(t3.x*20, 1 - t3.y*20);
			Vector3f n3 = m.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();

		glEndList();
		
		
		displaylist2 = glGenLists(1);
		glNewList(displaylist2, GL_COMPILE);

		Model m2 = null;
		
		
		try {
			m2 = LoaderObj.load(new File(loc2));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		glBegin(GL_TRIANGLES);
		for (Face face : m2.faces) {
			Vector2f t1 = m2.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glTexCoord2f(t1.x*20, 1 - t1.y*20);
			Vector3f n1 = m2.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m2.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m2.textures.get((int) face.textures.y - 1);
			glTexCoord2f(t2.x*20, 1 - t2.y*20);
			Vector3f n2 = m2.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m2.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m2.textures.get((int) face.textures.z - 1);
			glTexCoord2f(t3.x*20, 1 - t3.y*20);
			Vector3f n3 = m2.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m2.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();

		glEndList();
		
		displaylist3 = glGenLists(1);
		glNewList(displaylist3, GL_COMPILE);

		Model m3 = null;
		
		
		try {
			m3 = LoaderObj.load(new File(loc3));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		glBegin(GL_TRIANGLES);
		for (Face face : m3.faces) {
			Vector2f t1 = m3.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glTexCoord2f(t1.x*20, 1 - t1.y*20);
			Vector3f n1 = m3.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m3.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m3.textures.get((int) face.textures.y - 1);
			glTexCoord2f(t2.x*20, 1 - t2.y*20);
			Vector3f n2 = m3.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m3.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m3.textures.get((int) face.textures.z - 1);
			glTexCoord2f(t3.x*20, 1 - t3.y*20);
			Vector3f n3 = m3.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m3.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();

		glEndList();
		
		
		displaylist4 = glGenLists(1);
		glNewList(displaylist4, GL_COMPILE);

		Model m4 = null;
		
		
		try {
			m4 = LoaderObj.load(new File(loc4));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
	
		glBegin(GL_TRIANGLES);
		for (Face face : m4.faces) {
			Vector2f t1 = m4.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t1.x, 1 - t1.y);
			Vector3f n1 = m4.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m4.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m4.textures.get((int) face.textures.y - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t2.x, 1 - t2.y);
			Vector3f n2 = m4.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m4.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m4.textures.get((int) face.textures.z - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t3.x, 1 - t3.y);
			Vector3f n3 = m4.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m4.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();
		
		glEndList();
		
		
		displaylist5 = glGenLists(1);
		glNewList(displaylist5, GL_COMPILE);

		Model m5 = null;
		
		
		try {
			m5 = LoaderObj.load(new File(loc5));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
	
		glBegin(GL_TRIANGLES);
		for (Face face : m5.faces) {
			Vector2f t1 = m5.textures.get((int) face.textures.x - 1);
			// System.out.println((int)face.textures.x - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t1.x, 1 - t1.y*0.2f);
			Vector3f n1 = m5.normals.get((int) face.normal.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m5.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m5.textures.get((int) face.textures.y - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t2.x, 1 - t2.y*0.2f);
			Vector3f n2 = m5.normals.get((int) face.normal.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m5.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m5.textures.get((int) face.textures.z - 1);
			glMultiTexCoord2fARB(GL_TEXTURE0_ARB,t3.x, 1 - t3.y*0.2f);
			Vector3f n3 = m5.normals.get((int) face.normal.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m5.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

		}
		glEnd();
		
		glEndList();
		
		BufferedImage image = TextureLoader.loadImage("res/BrickOld.jpg");
		texture = TextureLoader.loadTexture(image);
		
		BufferedImage image2 = TextureLoader.loadImage("res/window.jpg");
		texture2 = TextureLoader.loadTexture(image2);
		
		BufferedImage image3 = TextureLoader.loadImage("res/metaljpg.jpg");
		texture3 = TextureLoader.loadTexture(image3);
		
	}
	
	public void build() {
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPushMatrix();
		glCallList(displaylist);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,40,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,80,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture3);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,120,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,160,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture3);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,200,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,240,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture3);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,280,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,320,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,360,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,400,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,440,0);
		glCallList(displaylist2);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,480,0);
		glCallList(displaylist3);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glCallList(displaylist4);
		glBindTexture(GL_TEXTURE_2D, texture);
		glCallList(displaylist5);
		glBindTexture(GL_TEXTURE_2D, texture);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,520,0);
		glCallList(displaylist2);
		glPopMatrix();
	}
	
	public void destroy() {
		glDeleteLists(displaylist, 1);
		glDeleteLists(displaylist2, 1);
		glDeleteLists(displaylist3, 1);
		glDeleteLists(displaylist4, 1);
		glDeleteLists(displaylist5, 1);
		glDeleteTextures(texture);
		glDeleteTextures(texture2);
		glDeleteTextures(texture3);



	}
	
	
}
