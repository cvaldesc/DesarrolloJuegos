package main;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;

import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glDrawRangeElements;




public class Sprite {
	
	boolean visible = true;
	float x, y, z;
	
	public Sprite(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		 
	}
	
	public void render() {
		
		
		glPushMatrix();
		glTranslatef(x, y, z);
		glRotatef(-Main.rotY, 0, 1.0f, 0);
		glRotatef(-Main.rotY2, 0, 1.0f, 0);
		glRotatef(-Main.rotX+2, 1.0f, 0, 0);
		
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, Main.vboid);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, Main.texid);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, Main.ind);
		glDrawRangeElements(GL_TRIANGLE_STRIP, 0, 6, 6, GL_UNSIGNED_INT, 0);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
		/*
		glBegin(GL_QUADS);
		
		glMultiTexCoord2fARB(GL_TEXTURE4_ARB, 0, 0);
		// glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 0);
		glVertex3f(-0.7f, 0.7f, 0);
		glMultiTexCoord2fARB(GL_TEXTURE4_ARB, 0, 1);
		// glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 0);
		glVertex3f(-0.7f, -0.7f, 0);
		glMultiTexCoord2fARB(GL_TEXTURE4_ARB, 1, 1);
		// glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 1);
		glVertex3f(0.7f, -0.7f, 0);
		glMultiTexCoord2fARB(GL_TEXTURE4_ARB, 1, 0);
		// glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 1);
		glVertex3f(0.7f, 0.7f, 0);
		
		glEnd();
		*/
		glPopMatrix();
		
		//glDepthMask(true); 
		
		
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		
	}
	
}
