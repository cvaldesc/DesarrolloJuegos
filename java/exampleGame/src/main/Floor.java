package main;

import static org.lwjgl.opengl.ARBMultitexture.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector3f;


public class Floor {
	// Class for creating simple Box(Cube)

	Vector3f c, d, g, h;
	int displaylistf;
	BufferedImage image;
	int texture;
	int texture2;

	public Floor(Vector3f c, Vector3f d, Vector3f g, Vector3f h) {
		// constructor

		this.c = c;
		this.d = d;

		this.g = g;
		this.h = h;

		loadTexture();
		createlist();
	}

	public void build() {
		glCallList(displaylistf); // display list
	}

	public void destroy() {
		glDeleteLists(displaylistf, 1); // removing display list
		glDeleteTextures(texture);
	}

	public void createlist() {
		// creating display list
		displaylistf = glGenLists(1);
		glNewList(displaylistf, GL_COMPILE);
		
		int gl = GL_QUADS;

		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture);
		//glActiveTextureARB(GL_TEXTURE5_ARB);
		//glEnable(GL_TEXTURE_2D);
		//glBindTexture(GL_TEXTURE_2D, texture2);
		//glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_ADD);

		 // bind floor texture
		glBegin(gl);
		glNormal3f(0,-1,0);

		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 0, 0);
		glMultiTexCoord2fARB(GL_TEXTURE5_ARB, 0, 0);
		glVertex3d(c.x, c.y, c.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 0, 10);
		glMultiTexCoord2fARB(GL_TEXTURE5_ARB, 1, 0);
		glVertex3d(d.x, d.y, d.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB,10, 10);
		glMultiTexCoord2fARB(GL_TEXTURE5_ARB, 1, 1);
		glVertex3d(h.x, h.y, h.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 10, 0);
		glMultiTexCoord2fARB(GL_TEXTURE5_ARB, 0, 1);
		glVertex3d(g.x, g.y, g.z);
		glEnd();

		glEndList();
		
		
	}
	

	public void loadTexture() {
		// loader for textures (using slick2d helper)
		image = TextureLoader.loadImage("res/FloorsRounded.jpg");
		texture = TextureLoader.loadTexture(image);
		BufferedImage image2 = TextureLoader.loadImage("res/Decals.jpg");
		texture2 = TextureLoader.loadTexture(image2);

	}
}
