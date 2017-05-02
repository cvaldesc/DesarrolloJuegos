package main;

import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE0_ARB;
import static org.lwjgl.opengl.ARBMultitexture.glActiveTextureARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector3f;


public class Walls {
	// Class for creating simple Box(Cube)

	Vector3f a, b, c, d, e, f, g, h;
	int displaylist;
	int texture;
	BufferedImage image;
	String tex;

	public Walls(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e,
			Vector3f f, Vector3f g, Vector3f h, String tex) {
		// constructor
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.tex = tex;

		image = TextureLoader.loadImage(tex);
		texture = TextureLoader.loadTexture(image);
	}
	
	public void destroy() {
		glDeleteTextures(texture);
	}

	public void build() {
		
		int gl = GL_QUADS;

		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture);
		
		 // bind wall texture
		
		glBegin(gl);
		glNormal3f(0,0,1);
		glTexCoord2f(0, 0);
		glVertex3d(b.x, b.y, b.z);
		glTexCoord2f(10, 0);
		glVertex3d(a.x, a.y, a.z);
		glTexCoord2f(10, 10);
		glVertex3d(d.x, d.y, d.z);
		glTexCoord2f(0, 10);
		glVertex3d(c.x, c.y, c.z);
		glEnd();

		glBegin(gl);
		glNormal3f(-1,0,0);
		glTexCoord2f(0, 0);
		glVertex3d(f.x, f.y, f.z);
		glTexCoord2f(10, 0);
		glVertex3d(b.x, b.y, b.z);
		glTexCoord2f(10, 10);
		glVertex3d(c.x, c.y, c.z);
		glTexCoord2f(0, 10);
		glVertex3d(g.x, g.y, g.z);
		glEnd();

		glBegin(gl);
		glNormal3f(1,0,0);
		glTexCoord2f(0, 0);
		glVertex3d(a.x, a.y, a.z);
		glTexCoord2f(10, 0);
		glVertex3d(e.x, e.y, e.z);
		glTexCoord2f(10, 10);
		glVertex3d(h.x, h.y, h.z);
		glTexCoord2f(0, 10);
		glVertex3d(d.x, d.y, d.z);
		glEnd();

		glBegin(gl);
		glNormal3f(0,0,-1);
		glTexCoord2f(0, 0);
		glVertex3d(e.x, e.y, e.z);
		glTexCoord2f(10, 0);
		glVertex3d(f.x, f.y, f.z);
		glTexCoord2f(10, 10);
		glVertex3d(g.x, g.y, g.z);
		glTexCoord2f(0, 10);
		glVertex3d(h.x, h.y, h.z);
		glEnd();
		
		
	}

	
}
