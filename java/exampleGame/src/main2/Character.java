package main2;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

public class Character {
	//Class for creating a Character
	
	Vector3f a,b,c,d, e, f, g, h, i, j, k ;
	int displaylistch; 

	public Character(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e, Vector3f f, Vector3f g, Vector3f h, Vector3f i, Vector3f j, Vector3f k) {
		//constructor
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.j = j;
		this.k = k;
		

		
	}

	public void build() {
		createlist(); 
		glCallList(displaylistch); //display list
	}
	
	public void destroy() {
		glDeleteLists(displaylistch, 1); //removing display list
	}
	
	public void createlist() {
		//creating display list
				displaylistch = glGenLists(1);  
				glNewList(displaylistch, GL_COMPILE);
				
				glBegin(GL_QUADS);
				glColor3f(0.0f, 0.0f, 0.0f);
				glVertex3d(a.x, a.y, a.z);
				glVertex3d(b.x, b.y, b.z);
				glColor3f(1.0f, 0.6f, 0.3f);
				glVertex3d(c.x, c.y, c.z);
				glVertex3d(e.x, e.y, e.z);
				glEnd();
				glColor3f(1.0f, 0.6f, 0.3f);
				glBegin(GL_LINES);
				glVertex3d(d.x, d.y, d.z);
				glVertex3d(k.x, k.y, k.z);
				glEnd();
				glColor3f(0.0f, 0.0f, 0.0f);
				glBegin(GL_LINES);
				glVertex3d(k.x, k.y, k.z);
				glVertex3d(f.x, f.y, f.z);
				glEnd();
				glColor3f(1.0f, 0.6f, 0.3f);
				glBegin(GL_LINES);
				glColor3f(0.0f, 0.0f, 0.0f);
				glVertex3d(k.x, k.y, k.z);
				glColor3f(1.0f, 0.6f, 0.3f);
				glVertex3d(g.x, g.y, g.z);
				glEnd();
				glColor3f(1.0f, 0.6f, 0.3f);
				glBegin(GL_LINES);
				glColor3f(0.0f, 0.0f, 0.0f);
				glVertex3d(k.x, k.y, k.z);
				glColor3f(1.0f, 0.6f, 0.3f);
				glVertex3d(h.x, h.y, h.z);
				glEnd();
				glColor3f(1.0f, 0.6f, 0.3f);
				glBegin(GL_LINES);
				glColor3f(0.0f, 0.0f, 0.0f);
				glVertex3d(f.x, f.y, f.z);
				glColor3f(1.0f, 0.6f, 0.3f);
				glVertex3d(i.x, i.y, i.z);
				glEnd();
				
				glBegin(GL_LINES);
				glColor3f(0.0f, 0.0f, 0.0f);
				glVertex3d(f.x, f.y, f.z);
				glColor3f(1.0f, 0.6f, 0.3f);
				glVertex3d(j.x, j.y, j.z);
				glEnd();
				
				glEndList();
	}

	public void add(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		
	//method for changing Objects vertices coordinate (x,y,z)
		
		this.a.x += a.x;
		this.a.y = a.y;
		this.a.z += a.z;

		this.b.x += b.x;
		this.b.y = b.y;
		this.b.z += b.z;
		
		this.c.x += c.x;
		this.c.y = c.y;
		this.c.z += c.z;
		
		this.d.x += d.x;
		this.d.y = d.y;
		this.d.z += d.z;
		
	}
	
	public void move(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		
		//method for changing Objects vertices coordinate (x,y,z)
			
			this.a.x = a.x;
			this.a.y = a.y;
			this.a.z = a.z;

			this.b.x = b.x;
			this.b.y = b.y;
			this.b.z = b.z;
			
			this.c.x = c.x;
			this.c.y = c.y;
			this.c.z = c.z;
			
			this.d.x = d.x;
			this.d.y = d.y;
			this.d.z = d.z;
			
		}
}
