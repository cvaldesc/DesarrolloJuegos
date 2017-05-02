package main;

import static org.lwjgl.opengl.ARBBufferObject.GL_STATIC_DRAW_ARB;
import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glBufferDataARB;
import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;

import static org.lwjgl.opengl.GL12.glDrawRangeElements;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public class Boxx {
	Vector3f a, b, c, d, e, f, g, h;

	//FloatBuffer texx;
	//IntBuffer buffer;
	//FloatBuffer Vertices;
	//FloatBuffer norm;
	int vboid;
	int ind;
	int texid;
	int norid;
	

	public Boxx(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e,
			Vector3f f, Vector3f g, Vector3f h) {
		// constructor
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;

		// vbo stuff
		float[] VerticesArray = new float[] { 
	
				b.x, b.y, b.z, 
				a.x, a.y, a.z, 
		        d.x, d.y, d.z,
		        c.x, c.y, c.z,
		       
		        f.x, f.y, f.z,
		        b.x, b.y, b.z,
		        c.x, c.y, c.z,
		        g.x, g.y, g.z,
		        
		        a.x, a.y, a.z,
		        e.x, e.y, e.z,
		        h.x, h.y, h.z,
		        d.x, d.y, d.z,
		        
		        b.x, b.y, b.z,
		        a.x, a.y, a.z,
		        e.x, e.y, e.z,
		        f.x, f.y, f.z,
		        
		        e.x, e.y, e.z,
		        f.x, f.y, f.z,
		        g.x, g.y, g.z,
		        h.x, h.y, h.z,
		        
		        c.x, c.y, c.z,
		        d.x, d.y, d.z,
		        h.x, h.y, h.z,
		        g.x, g.y, g.z
		
		};

		FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
		Vertices.put(VerticesArray);
		Vertices.rewind();
		// vbo
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer);
		vboid = buffer.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboid);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, Vertices, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
		int IndicesArray[] = new int[] {
				0, 1, 2,  2,3,0,
				4, 5, 6,  6,7,4,
				8,9,10,  10, 11, 8,
				12, 13,  14, 14, 15, 12,
				16, 17,  18, 18, 19, 16,
				20, 21,  22, 22, 23, 20,
				24, 25,  26, 26, 27, 24

		};

		IntBuffer Indices = BufferUtils.createIntBuffer(IndicesArray.length);
		Indices.put(IndicesArray);
		Indices.rewind();

		ind = glGenBuffersARB();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, ind);
		glBufferDataARB(GL_ELEMENT_ARRAY_BUFFER_ARB, Indices,
				GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		float[] textureArray = new float[] {

				0.0f, 0.0f,
				2.3f, 0.0f,
				2.3f, 0.3f,
				0.0f, 0.3f,
				
				0.0f, 0.0f,
				2.3f, 0.0f,
				2.3f, 0.3f,
				0.0f, 0.3f,
				
				0.0f, 0.0f,
				2.3f, 0.0f,
				2.3f, 0.3f,
				0.0f, 0.3f,
				
				0.0f, 0.0f,
				2.0f, 0.0f,
				2.0f, 2.0f,
				0.0f, 2.0f,
				
				0.0f, 0.0f,
				2.3f, 0.0f,
				2.3f, 0.3f,
				0.0f, 0.3f,
				
				0.0f, 0.0f,
				2.0f, 0.0f,
				2.0f, 2.0f,
				0.0f, 2.0f,

		};

		FloatBuffer texx = BufferUtils.createFloatBuffer(textureArray.length);
		texx.put(textureArray);
		texx.rewind();

		// col
		IntBuffer buffer3 = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer3);
		texid = buffer3.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, texid);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, texx, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
		
		
		float[] normalArray = new float[] {

				0,0,-1,
				0,0,-1,
				0,0,-1,
				0,0,-1,
				
				1,0,0,
				1,0,0,
				1,0,0,
				1,0,0,
				
				-1,0,0,
				-1,0,0,
				-1,0,0,
				-1,0,0,
				
				0,-1,0,
				0,-1,0,
				0,-1,0,
				0,-1,0,
				
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
				0,1,0,
				0,1,0,
				0,1,0,
				0,1,0,
		};

		FloatBuffer norm = BufferUtils.createFloatBuffer(normalArray.length);
		norm.put(normalArray);
		norm.rewind();

		IntBuffer buffer4 = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer4);
		norid = buffer4.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, norid);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, norm, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		
	}

	public void render() {

		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboid);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB,texid);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glEnableClientState(GL_NORMAL_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, norid);
		glNormalPointer(GL_FLOAT, 0, 0);
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, ind);
		glDrawRangeElements(GL_TRIANGLES, 0, 6, 36, GL_UNSIGNED_INT, 0);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		

	}
	
	public void destroy() {
		glDeleteBuffersARB(ind);
		glDeleteBuffersARB(norid);
		glDeleteBuffersARB(texid);
		glDeleteBuffersARB(vboid);
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
	
	
}
