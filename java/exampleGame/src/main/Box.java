package main;

import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE0_ARB;
import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE2_ARB;
//import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE3_ARB;

import static org.lwjgl.opengl.ARBMultitexture.glActiveTextureARB;
import static org.lwjgl.opengl.ARBMultitexture.glMultiTexCoord2fARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector3f;


public class Box {
	// Class for creating simple Box(Cube)

	Vector3f a, b, c, d, e, f, g, h;
	int displaylist;

	int texture;
	String textureloc;
	boolean visible = true;
	int count;
	BufferedImage image;

	public Box(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e,
			Vector3f f, Vector3f g, Vector3f h, String textureloc) {
		// constructor
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;

		this.textureloc = textureloc;

		image = TextureLoader.loadImage(textureloc);
		texture = TextureLoader.loadTexture(image);
		
	}

	public void build() {
		
		
		int gl = GL_QUADS;
		
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture); // bind wall texture
		
		glBegin(gl);
		glNormal3f(0,0,1);
		glTexCoord2f(0, 0);
		glVertex3d(b.x, b.y, b.z);
		glTexCoord2f(2.3f, 0);
		glVertex3d(a.x, a.y, a.z);
		glTexCoord2f(2.3f, 0.3f);
		glVertex3d(d.x, d.y, d.z);
		glTexCoord2f(0, 0.3f);
		glVertex3d(c.x, c.y, c.z);
		glEnd();
		
		glBegin(gl);
		glNormal3f(1,0,0);
		glTexCoord2f(0, 0);
		glVertex3d(f.x, f.y, f.z);
		glTexCoord2f(2.3f, 0);
		glVertex3d(b.x, b.y, b.z);
		glTexCoord2f(2.3f, 0.3f);
		glVertex3d(c.x, c.y, c.z);
		glTexCoord2f(0, 0.3f);
		glVertex3d(g.x, g.y, g.z);
		glEnd();
		
		glBegin(gl);
		glNormal3f(-1,0,0);
		glTexCoord2f(0, 0);
		glVertex3d(a.x, a.y, a.z);
		glTexCoord2f(2.3f, 0);
		glVertex3d(e.x, e.y, e.z);
		glTexCoord2f(2.3f, 0.3f);
		glVertex3d(h.x, h.y, h.z);
		glTexCoord2f(0, 0.3f);
		glVertex3d(d.x, d.y, d.z);
		glEnd();
		
		glBegin(gl);
		glNormal3f(0,-1,0);
		glTexCoord2f(0, 0);
		glVertex3d(b.x, b.y, b.z);
		glTexCoord2f(2, 0);
		glVertex3d(a.x, a.y, a.z);
		glTexCoord2f(2, 2);
		glVertex3d(e.x, e.y, e.z);
		glTexCoord2f(0, 2);
		glVertex3d(f.x, f.y, f.z);

		glEnd();
		
		
		glBegin(gl);
		glNormal3f(0,0,-1);
		glTexCoord2f(0, 0);
		glVertex3d(e.x, e.y, e.z);
		glTexCoord2f(2.3f, 0);
		glVertex3d(f.x, f.y, f.z);
		glTexCoord2f(2.3f, 0.3f);
		glVertex3d(g.x, g.y, g.z);
		glTexCoord2f(0, 0.3f);
		glVertex3d(h.x, h.y, h.z);
		glEnd();

		
		glBegin(gl);
		glNormal3f(0,1,0);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 0, 0);
		//glMultiTexCoord2fARB(GL_TEXTURE3_ARB, 0, 0);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 0);
		glVertex3d(c.x, c.y, c.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 2, 0);
		//glMultiTexCoord2fARB(GL_TEXTURE3_ARB, 2, 0);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 0);
		glVertex3d(d.x, d.y, d.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 2, 2);
		//glMultiTexCoord2fARB(GL_TEXTURE3_ARB, 2, 2);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 1);
		glVertex3d(h.x, h.y, h.z);
		glMultiTexCoord2fARB(GL_TEXTURE0_ARB, 0, 2);
		//glMultiTexCoord2fARB(GL_TEXTURE3_ARB, 0, 2);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 1);
		glVertex3d(g.x, g.y, g.z);
		glEnd();

		
	}
	
	public Vector3f getA() {
		return a;

	}

	public void setA(Vector3f a) {
		this.a = a;

	}

	public Vector3f getB() {
		return b;
	}

	public void setB(Vector3f b) {
		this.b = b;

	}

	public Vector3f getC() {
		return c;
	}

	public void setC(Vector3f c) {
		this.c = c;

	}

	public Vector3f getD() {
		return d;
	}

	public void setD(Vector3f d) {
		this.d = d;

	}

	public Vector3f getE() {
		return e;
	}

	public void setE(Vector3f e) {
		this.e = e;

	}

	public Vector3f getF() {
		return f;
	}

	public void setF(Vector3f f) {
		this.f = f;

	}

	public Vector3f getG() {
		return g;
	}

	public void setG(Vector3f g) {
		this.g = g;

	}

	public Vector3f getH() {
		return h;
	}

	public void setH(Vector3f h) {
		this.h = h;

	}

	public void setAllvert(float valueX, float valueY, float valueZ) {
		setA(new Vector3f(getA().x + valueX, getA().y + valueY, getA().z
				+ valueZ));
		setB(new Vector3f(getB().x + valueX, getB().y + valueY, getB().z
				+ valueZ));
		setC(new Vector3f(getC().x + valueX, getC().y + valueY, getC().z
				+ valueZ));
		setD(new Vector3f(getD().x + valueX, getD().y + valueY, getD().z
				+ valueZ));
		setE(new Vector3f(getE().x + valueX, getE().y + valueY, getE().z
				+ valueZ));
		setF(new Vector3f(getF().x + valueX, getF().y + valueY, getF().z
				+ valueZ));
		setG(new Vector3f(getG().x + valueX, getG().y + valueY, getG().z
				+ valueZ));
		setH(new Vector3f(getH().x + valueX, getH().y + valueY, getH().z
				+ valueZ));
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		
	}
	
	public void destroy() {
		glDeleteTextures(texture);
	}
	
}
