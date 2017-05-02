package main2;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

public class Box {
	//Class for creating simple Box(Cube)
	
	Vector3f a, b, c, d, e, f, g, h;
	int displaylist; 

	public Box(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Vector3f e,
			Vector3f f, Vector3f g, Vector3f h) {
		//constructor
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;

		
	}

	public void build() {
		createlist(); 
		glCallList(displaylist); //display list
	}
	
	public void destroy() {
		glDeleteLists(displaylist, 1); //removing display list
	}
	
	public void createlist() {
		//creating display list
				displaylist = glGenLists(1);  
				glNewList(displaylist, GL_COMPILE);
				
				glBegin(GL_QUADS);
				glColor3f(0.4f, 0.1f, 0.1f);
				glVertex3d(a.x, a.y, a.z);
				glVertex3d(b.x, b.y, b.z);
				glVertex3d(c.x, c.y, c.z);
				glVertex3d(d.x, d.y, d.z);
				glEnd();

				glBegin(GL_QUADS);
				glColor3f(1, 0.8f, 0.8f);

				glVertex3d(b.x, b.y, b.z);
				glVertex3d(f.x, f.y, f.z);
				glVertex3d(g.x, g.y, g.z);
				glVertex3d(c.x, c.y, c.z);
				glEnd();

				glBegin(GL_QUADS);
				glColor3f(1, 0.1f, 0.1f);

				glVertex3d(a.x, a.y, a.z);
				glVertex3d(e.x, e.y, e.z);
				glVertex3d(h.x, h.y, h.z);
				glVertex3d(d.x, d.y, d.z);
				glEnd();

				glBegin(GL_QUADS);
				glColor3f(0.6f, 0.3f, 0.3f);

				glVertex3d(b.x, b.y, b.z);
				glVertex3d(a.x, a.y, a.z);
				glVertex3d(e.x, e.y, e.z);
				glVertex3d(f.x, f.y, f.z);

				glEnd();
				glBegin(GL_QUADS);

				glColor3f(0.2f, 0.8f, 0.8f);

				glVertex3d(c.x, c.y, c.z);
				glVertex3d(g.x, g.y, g.z);
				glVertex3d(h.x, h.y, h.z);
				glVertex3d(d.x, d.y, d.z);
				glEnd();

				glBegin(GL_QUADS);
				glColor3f(0.4f, 0.4f, 0.4f);

				glVertex3d(e.x, e.y, e.z);
				glVertex3d(f.x, f.y, f.z);
				glVertex3d(g.x, g.y, g.z);
				glVertex3d(h.x, h.y, h.z);
				glEnd();
				
				glEndList();
	}

	public void move(Vector3f a, Vector3f b, Vector3f c, Vector3f d,
			Vector3f e, Vector3f f, Vector3f g, Vector3f h) {
		
	//method for changing Objects vertices coordinate (x,y,z)
		
		this.a.x += a.x;
		this.a.y += a.y;
		this.a.z += a.z;

		this.b.x += b.x;
		this.b.y += b.y;
		this.b.z += b.z;

		this.c.x += c.x;
		this.c.y += c.y;
		this.c.z += c.z;

		this.d.x += d.x;
		this.d.y += d.y;
		this.d.z += d.z;

		this.e.x += e.x;
		this.e.y += e.y;
		this.e.z += e.z;

		this.f.x += f.x;
		this.f.y += f.y;
		this.f.z += f.z;

		this.g.x += g.x;
		this.g.y += g.y;
		this.g.z += g.z;

		this.h.x += h.x;
		this.h.y += h.y;
		this.h.z += h.z;
	}
}
