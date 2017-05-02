package main2;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL15.*;
import static  org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;


import static org.lwjgl.opengl.GL11.*;


public class Main {

	private boolean running = false;

	private Vector3f pos = new Vector3f(0, 0, 0); // position vector
	private float rotY = 0; // rotation along Y axis
	private float rotY2 = 0; // rotation along Y axis
	private float view = 0; //for character view transformations (along z axis)

	// Quad variables
		private int vaoId = 0;
		private int vboId = 0;
		private int vboiId = 0;
		private int indicesCount = 0;
	
	
	public Main() {
		// set Display

		try {
			//define display with opengl 3.2 api
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
			.withForwardCompatible(true)
			.withProfileCore(true);

			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("twolwjgl");
			Display.create(pixelFormat, contextAtrributes);

		} catch (LWJGLException e) {
			System.err.println("LWJGLException! Can't create Display!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		//clear color(background) and set viewport
		glClearColor(0,0,0,0);
		glViewport(0,0,800,600);
	}

	public void init() {
		// initialize stuff
		
		//vertices to create quad
		float[] vertices = {
				-0.5f, 0.5f, 0f,	// Left top			ID: 0
				-0.5f, -0.5f, 0f,	// Left bottom		ID: 1
				0.5f, -0.5f, 0f,	// Right bottom		ID: 2
				0.5f, 0.5f, 0f		// Right left		ID: 3
			};
				// Sending data to OpenGL requires the usage of (flipped) byte buffers
				FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
				verticesBuffer.put(vertices);
				verticesBuffer.flip();
				
				//where points need to be
				byte[] indices = {
						// Left bottom triangle
						0, 1, 2,
						// Right top triangle
						2, 3, 0
				};
				indicesCount = indices.length;
				ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
				indicesBuffer.put(indices);
				indicesBuffer.flip();
				
				// Create a new Vertex Array Object in memory and select it (bind)
				// A VAO can have up to 16 attributes (VBO's) assigned to it by default
				vaoId = glGenVertexArrays();
				glBindVertexArray(vaoId);
				
				// Create a new Vertex Buffer Object in memory and select it (bind)
				// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
				vboId = glGenBuffers();
				glBindBuffer(GL_ARRAY_BUFFER, vboId);
				glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
				// Put the VBO in the attributes list at index 0
				glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
				// Deselect (bind to 0) the VBO
				glBindBuffer(GL_ARRAY_BUFFER, 0);
				
				// Deselect (bind to 0) the VAO
				glBindVertexArray(0);
				
				// Create a new VBO for the indices and select it (bind)
				vboiId = glGenBuffers();
				glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiId);
				glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
				// Deselect (bind to 0) the VBO
				glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		running = true;

	
	}

	public void start() {
		// start method

		while (running) {
			// game loop

			glClear(GL_COLOR_BUFFER_BIT);
			
			// Bind to the VAO that has all the information about the quad vertices
			glBindVertexArray(vaoId);
			glEnableVertexAttribArray(0);
			
			// Bind to the index VBO that has all the information about the order of the vertices
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiId);
			
			// Draw the vertices
			glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_BYTE, 0);
			
			
			// Put everything back to default (deselect)
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			
			// closing on "X"
			if (Display.isCloseRequested()) {
				stop();
			}

			readinput(); // read keys
			Display.update();
			Display.sync(60);
		}
		
		// Disable the VBO index from the VAO attributes list
				glDisableVertexAttribArray(0);
				
				// Delete the VBO
				glBindBuffer(GL_ARRAY_BUFFER, 0);
				glDeleteBuffers(vboId);
				
				// Delete the index VBO
				glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
				glDeleteBuffers(vboiId);
				
				// Delete the VAO
				glBindVertexArray(0);
				glDeleteVertexArrays(vaoId);
				
				Display.destroy();

		
	}

	public void stop() {
		// stop method
		running = false;
	}

	public void readinput() {
		// reading input(keyboard, mouse) method

		
		//rotation parameter max 360 degree
		if (rotY == 360 | rotY == -360)
			rotY = 0;

		if (rotY2 == 360 | rotY2 == -360)
			rotY2 = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			// moving code for key A
			float x = (float) (0.1 * Math.cos(Math.toRadians(180 - rotY)));
			float z = (float) (0.1 * Math.sin(Math.toRadians(180 - rotY)));
			pos.x -= x;
			pos.z += z;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			// moving code for key S

			float z = (float) (0.1 * Math.sin(Math.toRadians(90 - rotY)));
			float x = (float) (0.1 * Math.cos(Math.toRadians(90 - rotY)));

			pos.z -= z;
			pos.x += x;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			// moving code for key W

			float z = (float) (0.1 * Math.sin(Math.toRadians(90 - rotY)));
			float x = (float) (0.1 * Math.cos(Math.toRadians(90 - rotY)));

			pos.x -= x;
			pos.z += z;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			// moving code for key D

			float x = (float) (0.1 * Math.cos(Math.toRadians(180 - rotY)));
			float z = (float) (0.1 * Math.sin(Math.toRadians(180 - rotY)));

			pos.x += x;
			pos.z -= z;
			
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			// clockwise rotation for key E
			rotY += 3.0f;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// counter clockwise rotation for key Q

			rotY -= 3.0f;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			// clockwise rotation for key RIGHT (showing character's side)
			rotY2 += 3.0f;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			// counter clockwise rotation for key LEFT (showing character's side)

			rotY2 -= 3.0f;

		}

		//change view close or third person
		if (Keyboard.next()){
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			// gluLookAt(0.0f, 1.0f, 0.0f, 0.0f, 0.2f, 0, 0.0f, 1.0f, 0.0f);
			if (view == 8.0f)
			view = 0;
			else view  = 8.0f;
		}
		}
		
		

		//System.out.println("angle " + rotY + " z " + pos.z + " x " + pos.x);

	}
	
	

	public static void main(String[] args) {
		Main m = new Main();
		m.init();
		m.start();

	}

}
