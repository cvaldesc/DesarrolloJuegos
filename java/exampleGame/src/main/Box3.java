package main;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glDrawRangeElements;




public class Box3 {
	Vector3f a, b, c, d, e, f, g, h;
	float x, y, z, angle;
	boolean visible = true;
	
	public Box3(float x, float y, float z, Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e,
			Vector3f f, Vector3f g, Vector3f h) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		
		
		this.x = x;
		this.y = y;
		this.z = z;
		angle = 0.0f;
		 
	}
	
	public void render() {
		
		
		glPushMatrix();
		glRotatef(angle,1.0f,0.0f,0.0f);
		glTranslatef(x, y, z);
		
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, Main.vboid2);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB,Main.texid2);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glEnableClientState(GL_NORMAL_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, Main.norid2);
		glNormalPointer(GL_FLOAT, 0, 0);
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, Main.ind2);
		glDrawRangeElements(GL_TRIANGLES, 0, 6, 36, GL_UNSIGNED_INT, 0);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		glPopMatrix();
	
		
		
	}
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		
	}
	
	public Vector3f getA() {
		return a;

	}
	
	public void destroy() {
		glDeleteBuffersARB(Main.vboid2);
		glDeleteBuffersARB(Main.norid2);
		glDeleteBuffersARB(Main.texid2);
		glDeleteBuffersARB(Main.ind2);
	}

	public void setA(Vector3f a) {
		this.a = a;

	}
	
	public void setXYZ(float x, float y,float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		
	}
	
	public void setAllvert(float valueX, float valueY, float valueZ, float angle) {
		this.angle += angle;
		this.x += valueX;
		this.y += valueY;
		this.z += valueZ;
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
	
}
