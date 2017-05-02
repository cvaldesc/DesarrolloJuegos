package main;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

public class Particle {

	private boolean visible = true;
	private float x, y, z, x2, y2, z2;
	public float yVel, xVel, zVel;
	public int kk = 0;
	public int zz = 0;
	public int xx = 0;
	private Random random = new Random();
	public int set = 3;

	public Particle(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		x2 = x;
		y2 = y;
		z2 = z;
	}

	public void render() {

		glPushMatrix();
		glRotatef(kk, 0, 1, 0);
		glRotatef(xx, 1, 0, 0);
		glRotatef(zz, 0, 0, 1);
		glTranslatef(x, y, z);

		glCallList(Main.displaylistppp); // using display list
		// glBegin(GL_POINTS); //if using immediate mode
		// glVertex3f(x,y,z);
		// glEnd();

		// glEnableClientState(GL_VERTEX_ARRAY); //if using vbo
		// glBindBufferARB(GL_ARRAY_BUFFER_ARB, Main.vboidpp);
		// glVertexPointer(3, GL_FLOAT, 0, 0);
		// glDrawRangeElements(GL_POINTS, 0, 1, 1, GL_UNSIGNED_INT, 0);
		// glDrawArrays(GL_POINTS, 0,1000);
		// glDisableClientState(GL_VERTEX_ARRAY);

		glPopMatrix();

		// settings
		if (set == 0) {
			setting1();
		}
		if (set == 1) {
			setting2();
		}
		if (set == 2) {
			setting3();
		}
		if (set == 3) {
			setting4();
		}
	}

	public void setting1() {
		// particle physics

		float k = random.nextFloat();
		x = k;
		float c = random.nextFloat();
		z = c;
		kk += 2;
		++zz;
		++xx;
		if (kk >= 360) {
			kk = 0;
		}
	}

	public void setting2() {
		// particle physics
		yVel = yVel - 0.1f * Main.timeElapsed;

		xVel = xVel - 0.1f * Main.timeElapsed;
		zVel = zVel - 0.1f * Main.timeElapsed;
		float k = random.nextFloat();
		if (y <= -3) {
			yVel = k;
			y = -2.9f;
		}
		if (y > -3) {
			y = y + yVel;
		}

		if (x <= -18) {

			xVel = 0.6f * k;
			x = -17.5f;
		}
		if (x >= 18) {

			xVel = -0.6f * k;
			x = 17.5f;
		}
		if (x > -18 && x < 18) {
			x = x + xVel;
		}

		if (z <= -18) {
			zVel = 0.6f * k;
			z = -17.5f;
		}
		if (z >= 18) {
			zVel = -0.6f * k;
			z = 17.5f;
		}
		if (z > -18 && z < 18) {
			z = z + zVel;
		}
	}

	public void setting3() {
		// particle physics
		yVel = yVel - 0.1f * Main.timeElapsed;
		xVel = xVel - 0.1f * Main.timeElapsed;
		float k = random.nextFloat();
		if (y <= -3) {
			yVel = k;
			y = -2.9f;
		}
		if (y > -3) {
			y = y + yVel;
		}

		if (x <= -18) {
			xVel = 0.0f;
			x = -17.5f;
		}
		if (x >= 18) {
			xVel = -0.6f * k;
			x = 17.5f;
		}
		if (x > -18 && x < 18) {
			x = x + xVel;
		}

		if (z <= -18) {
			zVel = 0.6f * k;
			z = -17.5f;
		}
		if (z >= 18) {
			zVel = -0.6f * k;
			z = 17.5f;
		}
		if (z > -18 && z < 18) {
			z = z + zVel;
		}
	}

	public void setting4() {
		// particle physics

		float k = random.nextFloat();
		yVel = (float) (k * 0.1f);
		float c = random.nextFloat();
		xVel = (float) (Math.cos(k) * 0.3f);
		zVel = (float) (Math.sin(c) * 0.3f);
		if (y > -3) {
			y = y + yVel;
		}
		if (y >= 20) {
			y = -2;
			x = 0;
			z = 0;
		}
		if (x <= -18) {
			x = 0;
			z = 0;
			y = 0;
		}
		if (x >= 18) {
			x = 0;
			z = 0;
			y = 0;
		}
		if (x > -18 && x < 18) {
			x = x + xVel;
		}
		if (z <= -18) {
			z = 0;
			x = 0;
			y = 0;
		}
		if (z >= 18) {
			z = 0;
			x = 0;
			y = 0;
		}
		if (z > -18 && z < 18) {
			z = z + zVel;
		}
	}

	public void setting5() {
		// particle physics

		// particle physics
		// yVel = yVel - 0.1f * Main.timeElapsed;

		// xVel = xVel - 0.1f * Main.timeElapsed;
		// zVel = zVel - 0.1f * Main.timeElapsed;
		float k = random.nextFloat();
		// x = k;
		// yVel = (float) (k*0.1f);
		float c = random.nextFloat();
		// z = c;
		xVel = (float) (Math.cos(k) * 0.3f);
		// xVel = 0.1f;
		// float h = random.nextFloat();
		zVel = (float) (Math.sin(c) * 0.3f);
		// zVel = -0.90f;
		// System.out.println(kk);
		// kk += 2;
		// ++zz;
		// ++xx;
		// if (kk >= 360) {
		// kk = 0;
		// }
		// System.out.println(k);
		if (y <= -3) {
			// yVel = k;
			// y = -2.9f;
		}
		if (y > -3) {
			y = y + yVel;
		}
		if (y >= 20) {
			y = -2;
			x = 0;
			z = 0;
		}

		if (x <= -18) {
			x = 0;
			z = 0;
			y = 0;
			// xVel = 0.0f;
			// x = -17.5f;
		}
		if (x >= 18) {
			x = 0;
			z = 0;
			y = 0;
			// xVel = -0.6f*k;
			// x = 17.5f;
		}
		if (x > -18 && x < 18) {
			x = x + xVel;
		}

		if (z <= -18) {
			z = 0;
			x = 0;
			y = 0;
			// zVel = 0.6f*k;
			// z = -17.5f;
		}
		if (z >= 18) {
			z = 0;
			x = 0;
			y = 0;
			// zVel = -0.6f*k;
			// z = 17.5f;
		}
		if (z > -18 && z < 18) {
			z = z + zVel;
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;

	}

	public void resetCoords() {
		x = x2;
		y = y2;
		z = z2;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

}
