package main;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.ARBVertexBufferObject.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.opengl.ARBBufferObject.GL_STATIC_DRAW_ARB;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.ARBPointParameters.*;
import static org.lwjgl.opengl.ARBMultitexture.*;

public class Main {

	private boolean running = false;
	private boolean wire = false; // boolean for wireframe mode

	//private Walls[] walls; // array for walls
	private Floor floor; // floor object
	private Character character; // character object

	private int texturexx; // for mipmap texture
	private int textureyy; // for lightmap texture
	private boolean blend = false; // boolean for blending option

	private final float FOV = 60; // field of view
	private final int WIDTH = 800; // width of screen
	private final int HEIGHT = 600; // height of screen
	private final float ASPECT = (float) WIDTH / HEIGHT; // aspect

	private Vector3f pos = new Vector3f(0, 0, 0); // position vector
	private Vector3f posfix = new Vector3f(0, 0, 0); // fixed position vector
	private Vector3f camerapos = new Vector3f(0, 0, 0);
	static float rotY = 0; // rotation along Y axis (global)
	static float rotY2 = 0; // rotation along Y axis (character)
	static float rotX = 0; // rotation along X axis (character)
	private float view = 0; // for character view transformations (along z axis)
	private float viewdistane = 0;
	private float posYheight = 0.0f; // Y position of character
	private float z; // variable to move in z axis
	private float x; // variable to move in x axis

	private int count = 0; // counter for moving box
	private boolean side = false;

	// variables for jumping
	public static int height = 0; // height of jump
	private int maxHeight = 30; // max height
	private int countjump = 0; // counter (jump times)
	public boolean dontjump = false;
	public static boolean isJump = false;
	private int jumptimes = 2; // max jump times
	private float yVel;
	private float yAcc = 0.1f;
	boolean particleb = false;

	// fog parameters
	private float fogNear = 15f;
	private float fogFar = 30f;
	private Color fogColor = new Color(0f, 0f, 0f, 1f);

	//private String walltex = "res/wall.png";
	//private String walltex2 = "res/wall.png";
	private LevelGenerator level; // object level will create random cubes in
	private int displaylist;
	// scene
	private int countL = 262; // number of cubes (and apparently also height)
	private int countLF = 40; // number of cubes (and apparently also height)
	private int countLM = 40;
	boolean collide; // if collide then stop gravity

	private long lastFrame; // time at last frame
	public static float timeElapsed; // 10/fps
	private int fps; // fps
	private int FPS; // fps to show as text
	private long lastFPS; // last fps

	// lightning properties and objects
	private float mat_ambient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float light_position[] = { 0.0f, 6.0f, 3.0f, 1.0f };
	private float light_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	
	private float spot_direction[] = { 0.0f, 0.0f, -1.0f, 1.0f };

	// buffers for lightning
	private FloatBuffer buf; // ambient
	private FloatBuffer buf3; // position
	private FloatBuffer buf2;// diffuse
	private FloatBuffer buf4;// specular
	private FloatBuffer buf5;//spot direction

	public static boolean movingforward = false; // true if character is moving
	public static boolean movingback = false; // true if character is moving backward
	public static boolean movingsideright = false; // true if character is walking sideways
	public static boolean movingsideleft = false; // true if character is walking sideways
	private int texture2; // texture for copytex2
	private boolean texture; // boolean for rendertotexture
	private int texsplat; // particle texture
	private int texsplat2; // particle texture
	private int texsplat3; // particle texture
	private int texsplat4; // particle texture
	private int texsplat5; // particle texture
	private TextUtil textUtil = new TextUtil(); // textutil object
	private int textures; //sprite texture
	private int spritecounter; // spritecounter
	private boolean collect = false; // boolean to show 'Yay' etc (pop-up texts)
	private long frame; // in what frame apple was collected
	private Random random;
	private int k; // variable to randomize pop-up texts
	private float l = 0; // variable to set move for pop-up text
	// static Boxx boxx;

	// for vbo
	private FloatBuffer texx; // texture buffer for sprite
	private FloatBuffer Vertices; // vertices buffer for sprite
	public static int vboid; // vbo id for sprite
	public static int ind; // indicies id for sprite
	public static int texid; // texture id for sprite

	public static int vboid2; // vbo id for box3
	public static int ind2; // indicies id for box3
	public static int texid2; // texture id for box3
	public static int norid2; // normal id for box3
	private Particle[] particle; // particle object
	public static int displaylistppp; // display list for particle
	public static int vboidpp; // vbo id for partivle vbo (not used atm)
	// private float[] floatArray; // array for vbo
	private FloatBuffer qbuf; // float to count size of particle
	private float spost;
	private int displaylistblur;
	private int texcount = 0;
	private WallModel wallmodel; //new walls
	private double seconds; // how long game loads
	

	public Main() {
		// set Display
	
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("MTOWER");
			Display.setVSyncEnabled(true); 
			Display.create();

		} catch (LWJGLException e) {
			System.err.println("LWJGLException! Can't create Display!");
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void init() {
		// initialize stuff
		long start = System.nanoTime(); //start time
		initGL();
		createObjects();
		running = true;
		getDelta();
		lastFPS = getTime();
		long end = System.nanoTime();  //end time
		long elapsedTime = end - start; // tells how long game loads in nanoseconds
		seconds = (double)elapsedTime / 1000000000.0; //tells how long game loads in seconds
		
	}

	public void initGL() {
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(FOV, ASPECT, 0.3f, 60.0f);
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// view behind character
		gluLookAt(0.0f, 2.0f, 10.0f, 0.0f, 0, 0, 0.0f, 1.0f, 0.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_FOG);
		glEnable(GL_NORMALIZE);
		// buffer for colors
		FloatBuffer fogColours = BufferUtils.createFloatBuffer(4);
		fogColours.put(new float[] { fogColor.r, fogColor.g, fogColor.b,
				fogColor.a });
		glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
		fogColours.flip();

		// other fog parameters
		glFog(GL_FOG_COLOR, fogColours);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glHint(GL_FOG_HINT, GL_NICEST);
		glFogf(GL_FOG_START, fogNear);
		glFogf(GL_FOG_END, fogFar);
		glFogf(GL_FOG_DENSITY, 0.0005f);

		// buffers for lightning
		buf = BufferUtils.createFloatBuffer(mat_ambient.length);
		buf.put(mat_ambient, 0, mat_ambient.length);
		buf.flip();

		buf3 = BufferUtils.createFloatBuffer(light_position.length);
		buf3.put(light_position, 0, light_position.length);
		buf3.flip();

		buf2 = BufferUtils.createFloatBuffer(light_diffuse.length);
		buf2.put(light_diffuse, 0, light_diffuse.length);
		buf2.flip();

		buf4 = BufferUtils.createFloatBuffer(light_specular.length);
		buf4.put(light_specular, 0, light_specular.length);
		buf4.flip();
		
		buf5 = BufferUtils.createFloatBuffer(spot_direction.length);
		buf5.put(spot_direction, 0, spot_direction.length);
		buf5.flip();

		// parameters for lightning
		glShadeModel(GL_SMOOTH);

		glLight(GL_LIGHT0, GL_AMBIENT, buf);
		glLight(GL_LIGHT0, GL_DIFFUSE, buf2);
		glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, buf4);
		glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 50.0f);
		glLight(GL_LIGHT0, GL_POSITION, buf3);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, buf);
		
		//glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0f);
		//glLight(GL_LIGHT0, GL_SPOT_DIRECTION, buf5);
		//glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 5.0f);
		
		glEnable(GL_DEPTH_TEST);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_CULL_FACE);
	}

	public void createObjects() {

		float l = 30.0f; // helper parametr for floor

		// Floor vertices
		Vector3f c = new Vector3f(l, -3, -l);
		Vector3f d = new Vector3f(-l, -3, -l);
		Vector3f g = new Vector3f(l, -3, l);
		Vector3f h = new Vector3f(-l, -3, l);

		// create floor
		floor = new Floor(c, d, g, h);
		//int k = 0;

		// create walls
		//walls = new Walls[15];
		/*
		for (int i = 0; i < walls.length; i++) {

			Vector3f a2 = new Vector3f(-l, 32 + k, -l);
			Vector3f b2 = new Vector3f(l, 32 + k, -l);
			Vector3f c2 = new Vector3f(l, -3 + k, -l);
			Vector3f d2 = new Vector3f(-l, -3 + k, -l);

			Vector3f e2 = new Vector3f(-l, 32 + k, l);
			Vector3f f2 = new Vector3f(l, 32 + k, l);
			Vector3f g2 = new Vector3f(l, -3 + k, l);
			Vector3f h2 = new Vector3f(-l, -3 + k, l);

			k += 35;
			if (i % 2 == 0) {
				walls[i] = new Walls(a2, b2, c2, d2, e2, f2, g2, h2, walltex);
			} else {
				walls[i] = new Walls(a2, b2, c2, d2, e2, f2, g2, h2, walltex2);

			}

		}

		// create wall objects
		displaylist = glGenLists(1);
		glNewList(displaylist, GL_COMPILE);
		for (int i = 0; i < walls.length; i++) {
			walls[i].build();
		}
		glEndList();
		*/
		// create character
		character = new Character();
		character.createCharacter();

		// generate level
		level = new LevelGenerator(countL, countLF, countLM); // create and
																// generate

		loadTextures();

		random = new Random();
		createSpriteVBO();

		// make box3 vbo at initial position (then it will be translated)
		float xx = 0;
		float zz = 0;
		float gg = 0;

		createBox3VBO(new Vector3f(xx - 4, gg, zz - 4), new Vector3f(xx, gg,
				zz - 4), new Vector3f(xx, gg - 0.7f, zz - 4), new Vector3f(
				xx - 4, gg - 0.7f, zz - 4), new Vector3f(xx - 4, gg, zz),
				new Vector3f(xx, gg, zz), new Vector3f(xx, gg - 0.7f, zz),
				new Vector3f(xx - 4, gg - 0.7f, zz));

		// create particles
		particle = new Particle[400];

		for (int v = 0; v < particle.length; v++) {
			int xp = random.nextInt(5); // random value for Vector
			// int xp = 0;
			int yp = random.nextInt(5);
			// int yp = 0;
			int zp = random.nextInt(5); // values to also set random - values
			// int zp = 0;
			int kk = random.nextInt(2); // values to also set random - values
			int ll = random.nextInt(2);
			if (kk == 0)
				xp = -xp;

			if (ll == 0)
				zp = -zp;
			particle[v] = new Particle(xp, yp, zp);
		}

		// create 1 initial particle displaylist
		displaylistppp = glGenLists(1);
		glNewList(displaylistppp, GL_COMPILE);
		glBegin(GL_POINTS);
		glVertex3f(0, 0, 0);
		glEnd();
		glEndList();

		// createParticleVBO(); //use this when you want to test particle using
		// vbo

		// float to change size of particles
		float quadratic[] = { 1.0f, 0.0f, 0.01f, 1 };
		qbuf = BufferUtils.createFloatBuffer(quadratic.length);
		qbuf.put(quadratic);
		qbuf.rewind();

		//quad for blur
		displaylistblur = glGenLists(1);
		glNewList(displaylistblur, GL_COMPILE);
		glBegin(GL_QUADS);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0 + spost, 1 - spost);
		glVertex3f(0, 0, 0);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0 + spost, 0 + spost);
		glVertex3f(0, 600, 0);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1 - spost, 0 + spost);
		glVertex3f(800, 600, 0);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1 - spost, 1 - spost);
		glVertex3f(800, 0, 0);
		glEnd();
		glEndList();
		
		//init new walls
		wallmodel = new WallModel();

	}

	public void loadTextures() {
		// load texture to copytex2d
		BufferedImage imagee = TextureLoader.loadImage("res/lvl.png");
		texture2 = TextureLoader.loadMipTexture(imagee);

		// for multitexture texture 1
		BufferedImage image = TextureLoader.loadImage("res/lvl3.png");
		textureyy = TextureLoader.loadMipTexture(image);

		// for multitexture texture 2
		BufferedImage image2 = TextureLoader.loadImage("res/lightmap3.png");
		texturexx = TextureLoader.loadLightTexture(image2);

		// load sprite texture
		BufferedImage images = TextureLoader.loadImage("res/spriteapple.png");
		textures = TextureLoader.loadTexture(images);

		// load particle textures
		BufferedImage images3 = TextureLoader.loadImage("res/particle3.png");
		texsplat = TextureLoader.loadTexture(images3);

		BufferedImage images4 = TextureLoader.loadImage("res/particle.png");
		texsplat2 = TextureLoader.loadTexture(images4);

		BufferedImage images5 = TextureLoader.loadImage("res/particle2.png");
		texsplat3 = TextureLoader.loadTexture(images5);

		BufferedImage images6 = TextureLoader.loadImage("res/splat.png");
		texsplat4 = TextureLoader.loadTexture(images6);

		BufferedImage images7 = TextureLoader.loadImage("res/flash.png");
		texsplat5 = TextureLoader.loadTexture(images7);
	}

	public void createSpriteVBO() {

		// vbo stuff for sprite
		float[] VerticesArray = new float[] { -0.7f, 0.7f, 0.0f, -0.7f, -0.7f,
				0.f, 0.7f, -0.7f, 0.0f, 0.7f, 0.7f, 0.0f, };

		Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
		Vertices.put(VerticesArray);
		Vertices.rewind();
		// vbo id
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer);
		vboid = buffer.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboid);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, Vertices, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		// create indicies
		int IndicesArray[] = new int[] { 0, 1, 2, 0, 3, 2 };

		IntBuffer Indices = BufferUtils.createIntBuffer(IndicesArray.length);
		Indices.put(IndicesArray);
		Indices.rewind();

		ind = glGenBuffersARB();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, ind);
		glBufferDataARB(GL_ELEMENT_ARRAY_BUFFER_ARB, Indices,
				GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		// create texture
		float[] textureArray = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				1.0f, 1.0f, 0.0f };

		texx = BufferUtils.createFloatBuffer(textureArray.length);
		texx.put(textureArray);
		texx.rewind();

		IntBuffer buffer3 = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer3);
		texid = buffer3.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, texid);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, texx, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
	}

	public void createParticleVBO() {
		// test for one single vbo with all particles
		/*
		 * List<Float> floatList = new ArrayList<Float>();
		 * 
		 * for (int v = 0; v< length; v++) { int xp = random.nextInt(10); //
		 * random value for Vector int yp = random.nextInt(10); int zp =
		 * random.nextInt(10); // values to also set random - values int kk =
		 * random.nextInt(2); // values to also set random - values int ll =
		 * random.nextInt(2); if (kk == 0) xp = -xp;
		 * 
		 * if (ll == 0) zp = -zp; floatList.add((float)xp);
		 * floatList.add((float)yp); floatList.add((float)zp); }
		 * 
		 * floatArray = new float[floatList.size()];
		 * 
		 * for (int i = 0; i < floatList.size(); i++) { float f =
		 * floatList.get(i);
		 * 
		 * floatArray[i] = f; } System.out.println(floatList.size());
		 */

		// vbo stuff for single initial particle
		float[] VerticesArray = new float[] { 0.0f, 0.0f, 0.0f };

		FloatBuffer Verticespp = BufferUtils
				.createFloatBuffer(VerticesArray.length);
		Verticespp.put(VerticesArray);
		Verticespp.rewind();
		// vbo
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer);
		vboidpp = buffer.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboidpp);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, Verticespp, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

	}

	public void createBox3VBO(Vector3f a, Vector3f b, Vector3f c, Vector3f d,
			Vector3f e, Vector3f f, Vector3f g, Vector3f h) {

		// vbo vertices
		float[] VerticesArray = new float[] {

		b.x, b.y, b.z, a.x, a.y, a.z, d.x, d.y, d.z, c.x, c.y, c.z,

		f.x, f.y, f.z, b.x, b.y, b.z, c.x, c.y, c.z, g.x, g.y, g.z,

		a.x, a.y, a.z, e.x, e.y, e.z, h.x, h.y, h.z, d.x, d.y, d.z,

		b.x, b.y, b.z, a.x, a.y, a.z, e.x, e.y, e.z, f.x, f.y, f.z,

		e.x, e.y, e.z, f.x, f.y, f.z, g.x, g.y, g.z, h.x, h.y, h.z,

		c.x, c.y, c.z, d.x, d.y, d.z, h.x, h.y, h.z, g.x, g.y, g.z

		};

		FloatBuffer Vertices = BufferUtils
				.createFloatBuffer(VerticesArray.length);
		Vertices.put(VerticesArray);
		Vertices.rewind();
		// vbo
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer);
		vboid2 = buffer.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboid2);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, Vertices, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		// indicies
		int IndicesArray[] = new int[] { 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8,
				9, 10, 10, 11, 8, 12, 13, 14, 14, 15, 12, 16, 17, 18, 18, 19,
				16, 20, 21, 22, 22, 23, 20, 24, 25, 26, 26, 27, 24

		};

		IntBuffer Indices = BufferUtils.createIntBuffer(IndicesArray.length);
		Indices.put(IndicesArray);
		Indices.rewind();

		ind2 = glGenBuffersARB();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, ind2);
		glBufferDataARB(GL_ELEMENT_ARRAY_BUFFER_ARB, Indices,
				GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		// texture
		float[] textureArray = new float[] { 0.0f, 0.0f, 2.3f, 0.0f, 2.3f,
				0.3f, 0.0f, 0.3f, 0.0f, 0.0f, 2.3f, 0.0f, 2.3f, 0.3f, 0.0f,
				0.3f, 0.0f, 0.0f, 2.3f, 0.0f, 2.3f, 0.3f, 0.0f, 0.3f, 0.0f,
				0.0f, 2.0f, 0.0f, 2.0f, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f, 2.3f,
				0.0f, 2.3f, 0.3f, 0.0f, 0.3f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f,
				2.0f, 0.0f, 2.0f, };

		FloatBuffer texx = BufferUtils.createFloatBuffer(textureArray.length);
		texx.put(textureArray);
		texx.rewind();

		IntBuffer buffer3 = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer3);
		texid2 = buffer3.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, texid2);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, texx, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		// normals
		float[] normalArray = new float[] { 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0,
				-1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0, -1, 0, 0, -1,
				0, 0, -1, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0,
				1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				0, };

		FloatBuffer norm = BufferUtils.createFloatBuffer(normalArray.length);
		norm.put(normalArray);
		norm.rewind();

		IntBuffer buffer4 = BufferUtils.createIntBuffer(1);
		glGenBuffersARB(buffer4);
		norid2 = buffer4.get(0);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, norid2);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, norm, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

	}

	public int getDelta() {
		// method to get delta value
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public long getTime() {
		// actual time
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void updateFPS() {
		// fps counter
		if (getTime() - lastFPS > 1000) {
			FPS = fps;
			timeElapsed = (float) 10 / fps;

			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}

	public void start() {
		// start method

		while (running) {
			// game loop
			
			int delta = getDelta();
			
			checkCollisions(); // check for collisions
			readinput(delta); // read keys

			texture = true;
			if (blend) {
				renderToTexture();
			}
			texture = false;
			renderScene();
			if (blend) {
				blur();
			}
			renderHUD();
			// showWindow(); //window that show copy of screen
			renderSprite();

			// closing on "X"
			if (Display.isCloseRequested()) {
				stop();
			}
			Display.sync(60); // turn off to see max fps
			Display.update();
			
        
		}
		// delete after loop ends
		floor.destroy();
		wallmodel.destroy();
		character.destroy();
		level.destroy();
		glDeleteLists(displaylist, 1);
		//for (int i = 0; i < walls.length; i++) {
			//walls[i].destroy();
		//}
		glDeleteTextures(texture2);
		glDeleteTextures(texturexx);
		glDeleteTextures(textureyy);
		glDeleteTextures(textures);
		glDeleteTextures(texsplat);
		glDeleteTextures(texsplat2);
		glDeleteTextures(texsplat3);
		glDeleteTextures(texsplat4);
		glDeleteTextures(texsplat5);
		glDeleteBuffersARB(vboid);
		glDeleteBuffersARB(texid);
		glDeleteBuffersARB(ind);
		Display.destroy();
	}

	public void renderSprite() {

		// set all settings
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glDisable(GL_TEXTURE_2D);
		glActiveTextureARB(GL_TEXTURE4_ARB);
		glEnable(GL_TEXTURE_2D);
		glClientActiveTexture(GL_TEXTURE4_ARB);
		glBindTexture(GL_TEXTURE_2D, textures);
		glEnable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		level.generateSprites(); // generate sprites
		glActiveTextureARB(GL_TEXTURE4_ARB);
		glDisable(GL_TEXTURE_2D);
	}

	public void renderScene() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT
				| GL_STENCIL_BUFFER_BIT); // clear screen

		// wireframe or not
		if (wire) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glDisable(GL_FOG);
			glDisable(GL_LIGHT0);
			glDisable(GL_LIGHTING);

		} else {
			glEnable(GL_FOG);
			glEnable(GL_LIGHTING);
			glEnable(GL_LIGHT0);
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}

		
		glDepthFunc(GL_LEQUAL); // depth
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE); // culling
		glCullFace(GL_BACK);

		glActiveTextureARB(GL_TEXTURE1_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureyy);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

		glActiveTextureARB(GL_TEXTURE2_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texturexx);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		/*
		 * //multitextured test quad glBegin(GL_QUADS);
		 * glMultiTexCoord2fARB(GL_TEXTURE1_ARB, 0, 0);
		 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 0); glVertex3f(4, 5, 0);
		 * glMultiTexCoord2fARB(GL_TEXTURE1_ARB, 1, 0);
		 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 0); glVertex3f(4, 0, 0);
		 * glMultiTexCoord2fARB(GL_TEXTURE1_ARB, 1, 1);
		 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 1); glVertex3f(10, 0, 0);
		 * glMultiTexCoord2fARB(GL_TEXTURE1_ARB, 0, 1);
		 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 1); glVertex3f(10, 5, 0);
		 * glEnd();
		 */
		if (!texture) {
			glActiveTextureARB(GL_TEXTURE1_ARB);
			glDisable(GL_TEXTURE_2D);
			glActiveTextureARB(GL_TEXTURE0_ARB);
			glDisable(GL_TEXTURE_2D);
			glActiveTextureARB(GL_TEXTURE2_ARB);
			glDisable(GL_TEXTURE_2D);
			glActiveTextureARB(GL_TEXTURE3_ARB);
			glDisable(GL_TEXTURE_2D);
		}

		floor.build();

		glBindTexture(GL_TEXTURE_2D, 0);
		// render walls
		wallmodel.build();
		//glCallList(displaylist);
		glDisable(GL_CULL_FACE);

		glColor4f(1.0f, 1.0f, 1.0f, 0.55f);
		glEnable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_SRC_ALPHA);
		glEnable(GL_BLEND);
		glDisable(GL_LIGHTING);
		//glDisable(GL_DEPTH_TEST);
		// render level
		glPushMatrix();
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureyy);
		// level.callNormalBlock(); //old render sing display list
		glClientActiveTexture(GL_TEXTURE0_ARB);
		glBindTexture(GL_TEXTURE_2D, level.texture1);
		level.rNormalBlock3();
		glBindTexture(GL_TEXTURE_2D, level.texture2);
		level.rNormalBlock4();
		glPopMatrix();

		glPushMatrix();
		// glTranslatef(0, boxY, 0);
		// level.generateFallingBlock(); //old render using immediate mode
		glBindTexture(GL_TEXTURE_2D, level.texture3);
		level.generateFallingBlock3();
		// level.callFallingBlock();
		glPopMatrix();

		glPushMatrix();
		// glTranslatef(boxX, 0, 0);
		// level.callMovingBlock(); //old way using displaylist
		glBindTexture(GL_TEXTURE_2D, level.texture1);
		level.generateMovingBlock3a();
		glBindTexture(GL_TEXTURE_2D, level.texture2);
		level.generateMovingBlock3b();
		glPopMatrix();
		glDisable(GL_BLEND);
		glEnable(GL_LIGHTING);
		//glEnable(GL_DEPTH_TEST);
		glLoadIdentity();

		// character translation and rotation
		glTranslatef(0, 0, view);
		glRotatef(rotY2, 0, 1.0f, 0);
		glRotatef(rotX, 1.0f, 0.0f, 0);
		// render character
		if (!texture) {
		character.build();
		}
		posfix.x = -pos.x; // fixed value of pos (x axis)
		posfix.z = -pos.z; // fixed value of pos (z axis)
		// camerapos.x = pos.x +10;
		camerapos.x = (float) (Math.cos(Math.toRadians(rotY)) * 0
				+ Math.sin(Math.toRadians(rotY)) * 10 + pos.x);
		camerapos.y = posYheight -2;
		// camerapos.z = pos.z +10;
		camerapos.z = (float) (Math.sin(Math.toRadians(rotY)) * 0
				- Math.cos(Math.toRadians(rotY)) * 10 + pos.z);

		//another way - using camerapos.x = pos.x +10 it hides player when he get close to wall
		// if (camerapos.z >=25 | camerapos.z <-5 | camerapos.x >25 |
		// camerapos.x <-5 ) {
		// view += 0.1f;
		// if (view >=10.5f)
		// view =10.5f;
		// }else{
		// view -= 0.1f;
		// if (view <=0)
		// view = 0.0f;
		// }
		// System.out.println(rotY);

		//'camera collision' with wall
		if (camerapos.z >= 18 | camerapos.z <= -18 | camerapos.x >= 18
				| camerapos.x <= -18) {
			view += 2.0f;
			if (view >= 9.5f)
				view = 9.5f;
		} else {
			view -= 0.5f;
			if (view <= viewdistane)
				view = viewdistane;
		}
		// translate for y character axis
		glTranslatef(0, -posYheight, 0);

		glLineWidth(1.0f);

		// world translation and rotation
		if (rotY >= 360 & rotY > 0)
			rotY = 0;

		if (rotY <= -360 & rotY < 0)
			rotY = 0;

		glRotatef(0, 0, 0, 1.0f);
		glRotatef(rotY, 0, 1.0f, 0);
		glTranslatef(pos.x, 0, pos.z);
		posfix.y = posYheight;

		// if render particle - if not render, clean particle positions and
		// translations
		if (particleb) {
			renderParticle();
		} else {
			for (int v = 0; v < particle.length; v++) {
				particle[v].resetCoords();
				particle[v].kk = 0;
				particle[v].xx = 0;
				particle[v].zz = 0;
				particle[v].yVel = 0;
				particle[v].xVel = 0;
				particle[v].zVel = 0;
			}
		}
	}

	public void renderParticle() {
		
		glDisable(GL_LIGHTING);
		glDisable(GL_LIGHT1);

		glDepthMask(false);
		glEnable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_COLOR);
		glPointSize(8.0f);
		glEnable(GL_POINT_SPRITE_ARB);
		glPointParameterARB(GL_POINT_DISTANCE_ATTENUATION_ARB, qbuf);
		glPointParameterfARB(GL_POINT_FADE_THRESHOLD_SIZE_ARB, 4.0f);
		glPointParameterfARB(GL_POINT_SIZE_MAX_ARB, 8.0f);
		glPointParameterfARB(GL_POINT_SIZE_MIN_ARB, 2.0f);
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		glEnable(GL_BLEND);
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		if (texcount == 0)
			glBindTexture(GL_TEXTURE_2D, texsplat);

		if (texcount == 1)
			glBindTexture(GL_TEXTURE_2D, texsplat2);

		if (texcount == 2)
			glBindTexture(GL_TEXTURE_2D, texsplat3);

		if (texcount == 3)
			glBindTexture(GL_TEXTURE_2D, texsplat4);

		if (texcount == 4)
			glBindTexture(GL_TEXTURE_2D, texsplat5);

		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glClientActiveTexture(GL_TEXTURE0_ARB);
		// glEnableClientState(GL_VERTEX_ARRAY);//some old render using vbos
		// glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboidpp);
		// glVertexPointer(3, GL_FLOAT, 0, 0);
		for (int v = 0; v < particle.length; v++) {
			particle[v].render();
		}
		// glEnableClientState(GL_VERTEX_ARRAY);
		// glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboidpp);
		// glVertexPointer(3, GL_FLOAT, 0, 0);
		// glDrawRangeElements(GL_POINTS, 0, 1, length, GL_UNSIGNED_INT, 0);
		// glDrawElements(GL_POINTS, floatArray.length, GL_UNSIGNED_INT,0);
		// glDrawArrays(GL_POINTS, 0,length);
		// glDisableClientState(GL_VERTEX_ARRAY);
		glDisable(GL_POINT_SPRITE);
		glDisable(GL_BLEND);
		glDepthMask(true);

	}

	public void renderHUD() {

		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, 800, 600, 0, -1, 1); // Ortho Mode
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();

		glDisable(GL_FOG);
		glDisable(GL_LIGHTING);
		glDisable(GL_LIGHT1);
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);

		glEnable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);

		glActiveTextureARB(GL_TEXTURE0_ARB);
		glDisable(GL_TEXTURE_2D);
		glActiveTextureARB(GL_TEXTURE2_ARB);
		glDisable(GL_TEXTURE_2D);
		glActiveTextureARB(GL_TEXTURE1_ARB);
		glDisable(GL_TEXTURE_2D);

		// String textt = " -.:ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String fps = "FPS: " + Integer.toString(FPS);
		float posx = pos.x;
		float posz = pos.z;
		String ppos = "PLAYER POSITION:";
		String posX = "X: " + Float.toString(posx);
		String posY = "Y: " + posYheight;
		String posZ = "Z: " + Float.toString(posz);
		glColor4f(1.0f, 1.0f, 0.9f, 0.9f);
		// draw HUD text
		textUtil.drawText(fps, 10, 10);
		textUtil.drawText("GAME LOADED IN: " + seconds + " SECONDS", 10, 20);
		glColor4f(1.0f, 1.0f, 1.0f, 0.7f);
		textUtil.drawText(ppos, 10, 30);
		textUtil.drawText(posX, 15, 40);
		textUtil.drawText(posY, 15, 50);
		textUtil.drawText(posZ, 15, 60);
		textUtil.drawText("ROTATION: " + rotY, 15, 70);
		glColor4f(0.3f, 0.9f, 1.0f, 0.7f);
		textUtil.drawText("CAMERA POSITION: ", 10, 80);
		textUtil.drawText("X: " + camerapos.x, 15, 90);
		textUtil.drawText("Y: " + camerapos.y, 15, 100);
		textUtil.drawText("Z: " + camerapos.z, 15, 110);
		glColor4f(0.3f, 0.9f, 0.7f, 0.7f);
		textUtil.drawText("JUMP COUNTER: " + countjump, 10, 120);
		textUtil.drawText("Y VELOCITY: " + yVel, 10, 130);
		textUtil.drawText("JUMP HEIGHT: " + height, 10, 140);
		glColor4f(0.9f, 0.0f, 0.0f, 0.6f);
		textUtil.drawText("APPLES COLLECTED: " + spritecounter, 10, 150);

		// if apple picked up, show text
		if (collect) {
			glColor4f(0.0f, 0.8f, 0.8f, 0.8f);

			l += 0.9f;
			if (k == 0)
				textUtil.drawText("KEEP COLLECTING :D", 330 - (l),
						230 - (l - 0.7f));
			if (k == 1)
				textUtil.drawText("YAY !", 395 - (l), 230 - (l - 0.7f));
			if (k == 2)
				textUtil.drawText("YUMYYY ;>", 380 - (l), 230 - (l - 0.7f));

		}
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		// back to perspective view
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();

	}

	public void showWindow() {

		glViewport(0, 0, 800, 600);
		glEnable(GL_TEXTURE_2D); // Enable 2D Texture Mapping
		glEnable(GL_DEPTH_TEST); // Disable Depth Testing

		glActiveTextureARB(GL_TEXTURE2_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture2);

		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

		glBegin(GL_QUADS);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 1);
		glVertex3f(-20, 15, -19);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0, 0);
		glVertex3f(-20, -3, -19);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 0);
		glVertex3f(0, -3, -19);
		glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1, 1);
		glVertex3f(0, 15, -19);
		glEnd();

		glActiveTextureARB(GL_TEXTURE2_ARB);
		glDisable(GL_TEXTURE_2D);
	}

	public void renderToTexture() {
		glViewport(0, 0, 100, 100);
		renderScene();

		glDisable(GL_FOG);
		glDisable(GL_LIGHTING);

		glBindTexture(GL_TEXTURE_2D, texture2);
		glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, 0, 0, 100, 100, 0);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear The Screen
															// And Depth Buffer
		glViewport(0, 0, 800, 600);
		// glEnable(GL_FOG);
		// glEnable(GL_LIGHTING);
	}

	public void blur() {
		spost = 0.0f; // Texture Coordinate Offset

		// Disable AutoTexture Coordinates
		glDisable(GL_TEXTURE_GEN_S);
		glDisable(GL_TEXTURE_GEN_T);

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE); // Set Blending
		glEnable(GL_BLEND); // Enable Blending

		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, 800, 600, 0, -1, 1); // Ortho
		glMatrixMode(GL_MODELVIEW); //
		glPushMatrix();
		glLoadIdentity();

		glActiveTextureARB(GL_TEXTURE2_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture2);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		for (int num = 0; num < 2; num++) // Number Of Times To Render Blur
		{
			/*
			 * glBegin(GL_QUADS); glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0 +
			 * spost, 1 - spost); glVertex3f(0, 0, 0);
			 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 0 + spost, 0 + spost);
			 * glVertex3f(0, 600, 0); glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1 -
			 * spost, 0 + spost); glVertex3f(800, 600, 0);
			 * glMultiTexCoord2fARB(GL_TEXTURE2_ARB, 1 - spost, 1 - spost);
			 * glVertex3f(800, 0, 0); glEnd();
			 */
			glCallList(displaylistblur);
			spost += 0.009f; // Increase spost
		}

		glActiveTextureARB(GL_TEXTURE2_ARB);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		// back to perspective view
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}

	public void stop() {
		// stop method
		running = false;
	}

	public void readinput(int delta) {
		// reading input(keyboard, mouse) method
		movingforward = false;
		movingback = false;
		movingsideright = false;
		movingsideleft = false;

		// mouse rotation
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX() * 0.2f;
			float mouseDY = Mouse.getDY() * 0.2f;
			if ((mouseDX + rotY) > 0) {
				rotY += mouseDX;
			} else {
				if ((mouseDX + rotY) < 0) {
					rotY += mouseDX;
				}
			}
			if ((rotX - mouseDY) < 30 && (rotX - mouseDY) > -15) {
				rotX += -mouseDY;
			} else {
				if ((rotX - mouseDY) >= 30) {
					rotX = 29.5f;
				} else {
					if ((rotX - mouseDY) <= -15) {
						rotX = -14.5f;
					}
				}
			}
		}

		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				Mouse.setGrabbed(true);
			}
			if (Mouse.isButtonDown(1)) {
				Mouse.setGrabbed(false);
			}

		}

		// rotation parameter max 360 degree
		if (rotY == 360 | rotY == -360)
			rotY = 0;

		if (rotY2 == 360 | rotY2 == -360)
			rotY2 = 0;

		if (rotX == 360 | rotX == -360)
			// rotX = 0;

			if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)
					&& !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				// rotate back(Y axis) when view keys not pressed
				rotY2 = 0;
			}

		if (!Keyboard.isKeyDown(Keyboard.KEY_UP)
				&& !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			// rotate back(X axis) when view keys not pressed
			// rotX = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_S)
				&& !Keyboard.isKeyDown(Keyboard.KEY_D)
				&& Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key A and W
			x = (float) (0.1 * Math.sin(Math.toRadians(45 - rotY)) * delta * 0.1f);
			z = (float) (0.1 * Math.cos(Math.toRadians(45 - rotY)) * delta * 0.1f);
			movingforward = true;
			movingback = false;
			movingsideright = false;
			movingsideleft = false;
			pos.x += x;
			pos.z += z;
		}

		if (!Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_S)
				&& Keyboard.isKeyDown(Keyboard.KEY_D)
				&& Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key D and W
			x = (float) -(0.1 * Math.cos(Math.toRadians(45 - rotY)) * delta * 0.1f);
			z = (float) (0.1 * Math.sin(Math.toRadians(45 - rotY)) * delta * 0.1f);
			movingforward = true;
			movingback = false;
			movingsideright = false;
			movingsideleft = false;
			pos.x += x;
			pos.z += z;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_S)
				&& !Keyboard.isKeyDown(Keyboard.KEY_D)
				&& !Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key A
			x = (float) -(0.1 * Math.cos(Math.toRadians(180 - rotY)) * delta * 0.1f);
			z = (float) (0.1 * Math.sin(Math.toRadians(180 - rotY)) * delta * 0.1f);
			movingforward = false;
			movingback = false;
			movingsideright = true;
			movingsideleft = false;
			pos.x += x;
			pos.z += z;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				&& !Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_D)
				&& !Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key S

			z = (float) -(0.1 * Math.sin(Math.toRadians(90 - rotY)) * delta * 0.1f);
			x = (float) (0.1 * Math.cos(Math.toRadians(90 - rotY)) * delta * 0.1f);
			movingforward = false;
			movingback = true;
			movingsideright = false;
			movingsideleft = false;
			pos.z += z;
			pos.x += x;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_S)
				&& !Keyboard.isKeyDown(Keyboard.KEY_D)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key W

			z = (float) (0.1 * Math.sin(Math.toRadians(90 - rotY)) * delta  *0.1 );
			x = (float) -(0.1 * Math.cos(Math.toRadians(90 - rotY))  * delta  *0.1);
			movingforward = true;
			movingback = false;
			movingsideright = false;
			movingsideleft = false;
			pos.x += x;
			pos.z += z;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)
				&& !Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_S)
				&& !Keyboard.isKeyDown(Keyboard.KEY_W)
				&& !Keyboard.isKeyDown(Keyboard.KEY_E)
				&& !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			// moving code for key D

			x = (float) (0.1 * Math.cos(Math.toRadians(180 - rotY)) * delta * 0.1f);
			z = (float) -(0.1 * Math.sin(Math.toRadians(180 - rotY)) * delta * 0.1f);
			movingforward = false;
			movingback = false;
			movingsideright = true;
			movingsideleft = false;
			pos.x += x;
			pos.z += z;
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
			// counter clockwise rotation for key LEFT (showing character's
			// side)
			rotY2 -= 3.0f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			// counter clockwise rotation for key UP
			rotX += 3.0f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			// counter clockwise rotation for key DOWN (showing character's
			// side)
			rotX -= 3.0f;
		}

		if (Keyboard.next()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
				for (int v = 0; v < particle.length; v++) {
					// System.out.println(particle[v].set);
					if (particle[v].set == 0) {
						particle[v].set = 1;

					} else {

						if (particle[v].set == 1) {
							particle[v].set = 2;
						} else {

							if (particle[v].set == 2) {
								particle[v].set = 3;
							} else {
								if (particle[v].set == 3) {
									particle[v].set = 0;
								}
							}
						}
					}

				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				++texcount;
				if (texcount >= 5)
					texcount = 0;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				if (!wire) {
					wire = true;
				} else
					wire = false;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
				if (!blend) {
					blend = true;
				} else
					blend = false;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
				if (!particleb) {
					particleb = true;
				} else
					particleb = false;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
				// change player's view
				if (viewdistane == 7.0f)
					viewdistane = 0;
				else
					viewdistane = 7.0f;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !dontjump) {
				// jump counter
				isJump = true;

				if (countjump >= jumptimes) {
					dontjump = true;
					height = 0;
				} else {
					if (countjump < jumptimes) {
						countjump++;
						height = 0;
					}
				}
			}
			// max height for jump
			if (height >= maxHeight && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)
					&& !dontjump && !collide) {
				height = 0;
				yVel = 0;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !dontjump) {
			// jump
			if (height < maxHeight) {
				posYheight += 0.08f;
				yVel = 0.3f;
			}
			++height;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			// floating up
			yVel = 1;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			// floating up
			level.boxx3[0].setAllvert(0.0f, 0.1f, 0, 0);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			for (int i = 0; i < level.fallingboxNumber; i++) {
				level.boxx2f[i].setAllvert(0, -0.25f, 0, 0);
			}
			if (collide = true) {
			}
		}
		updateFPS();
	}

	public void checkNormalBox() {

		// collision checks for level cubes, also collision response
		for (int i = 0; i < level.boxNumber; i++) {
			if (posYheight >= level.box[i].getC().y - 1
					&& posYheight <= level.box[i].getA().y + 4) {
				if (posfix.x >= level.box[i].getA().x
						& posfix.x <= level.box[i].getB().x) {

					if (posfix.z > level.box[i].getA().z
							& posfix.z < level.box[i].getE().z) {
						/*
						 * if ((int) posYheight >= level.box[i].getC().y - 1) {
						 * // posYheight -= 0.5f ; collide = true; isJump =
						 * false; } else { collide = false; isJump = true; }
						 * 
						 * if ((int) posYheight <= level.box[i].getA().y + 2.3f)
						 * { posYheight += 0.25f; collide = true; isJump =
						 * false; dontjump = false; countjump = 0; height = 0; }
						 * else { collide = false; isJump = true; }
						 * 
						 * if (posYheight < level.box[i].getC().y + 0.9f) {
						 * collide = true; posYheight -= 0.25f; } collide =
						 * false;
						 */
						if (posYheight <= level.box[i].getA().y + 2.95f
								&& posYheight > level.box[i].getA().y + 2) {
							posYheight = level.box[i].getA().y + 2.95f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
							isJump = false;
							dontjump = false;
							countjump = 0;
							height = 0;
						} else {
							collide = false;
							isJump = true;
						}

						if (posYheight <= level.box[i].getA().y - 1.0f
								&& posYheight > level.box[i].getA().y - 1.35f) {
							posYheight = level.box[i].getA().y - 1.35f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
						} else {
							collide = false;

						}

						if ((int) posfix.x >= level.box[i].getA().x
								&& posYheight <= level.box[i].getA().y + 2.5f
								&& posYheight >= level.box[i].getC().y) {
							pos.x -= x;
						} else {

							if ((int) posfix.x <= level.box[i].getB().x
									&& posYheight <= level.box[i].getA().y + 2.5f
									&& posYheight >= level.box[i].getC().y) {
								pos.x -= x;
							}
						}

						if ((int) posfix.z >= level.box[i].getA().z
								&& posYheight <= level.box[i].getA().y + 2.5f
								&& posYheight >= level.box[i].getC().y) {
							pos.z -= z;
						} else {

							if ((int) posfix.z <= level.box[i].getE().z
									&& posYheight <= level.box[i].getA().y + 2.5f
									&& posYheight >= level.box[i].getC().y) {
								pos.z -= z;
							}
						}
					}
				}
			} else
				collide = false;
		}
	}

	public void checkNormalBoxx() {
		// collision checks for level cubes, also collision response
		for (int i = 0; i < level.boxNumber; i++) {
			if (posYheight >= level.boxx[i].getC().y - 1
					&& posYheight <= level.boxx[i].getA().y + 4) {
				if (posfix.x >= level.boxx[i].getA().x
						& posfix.x <= level.boxx[i].getB().x) {

					if (posfix.z > level.boxx[i].getA().z
							& posfix.z < level.boxx[i].getE().z) {
						/*
						 * if ((int) posYheight >= level.box[i].getC().y - 1) {
						 * // posYheight -= 0.5f ; collide = true; isJump =
						 * false; } else { collide = false; isJump = true; }
						 * 
						 * if ((int) posYheight <= level.box[i].getA().y + 2.3f)
						 * { posYheight += 0.25f; collide = true; isJump =
						 * false; dontjump = false; countjump = 0; height = 0; }
						 * else { collide = false; isJump = true; }
						 * 
						 * if (posYheight < level.box[i].getC().y + 0.9f) {
						 * collide = true; posYheight -= 0.25f; } collide =
						 * false;
						 */
						if (posYheight <= level.boxx[i].getA().y + 2.95f
								&& posYheight > level.boxx[i].getA().y + 2) {
							posYheight = level.boxx[i].getA().y + 2.95f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
							isJump = false;
							dontjump = false;
							countjump = 0;
							height = 0;
						} else {
							collide = false;
							isJump = true;
						}

						if (posYheight <= level.boxx[i].getA().y - 1.0f
								&& posYheight > level.boxx[i].getA().y - 1.35f) {
							posYheight = level.boxx[i].getA().y - 1.35f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
						} else {
							collide = false;

						}

						if ((int) posfix.x >= level.boxx[i].getA().x
								&& posYheight <= level.boxx[i].getA().y + 2.5f
								&& posYheight >= level.boxx[i].getC().y) {
							pos.x -= x;
						} else {

							if ((int) posfix.x <= level.boxx[i].getB().x
									&& posYheight <= level.boxx[i].getA().y + 2.5f
									&& posYheight >= level.boxx[i].getC().y) {
								pos.x -= x;
							}
						}

						if ((int) posfix.z >= level.boxx[i].getA().z
								&& posYheight <= level.boxx[i].getA().y + 2.5f
								&& posYheight >= level.boxx[i].getC().y) {
							pos.z -= z;
						} else {

							if ((int) posfix.z <= level.boxx[i].getE().z
									&& posYheight <= level.boxx[i].getA().y + 2.5f
									&& posYheight >= level.boxx[i].getC().y) {
								pos.z -= z;
							}
						}
					}
				}
			} else
				collide = false;
		}
	}

	public void checkNormalBox3() {
		// collision checks for level cubes, also collision response
		for (int i = 0; i < level.boxNumber; i++) {
			if (posYheight >= level.boxx3[i].getC().y - 1
					&& posYheight <= level.boxx3[i].getA().y + 4) {
				if (posfix.x >= level.boxx3[i].getA().x
						& posfix.x <= level.boxx3[i].getB().x) {

					if (posfix.z > level.boxx3[i].getA().z
							& posfix.z < level.boxx3[i].getE().z) {
						/*
						 * if ((int) posYheight >= level.box[i].getC().y - 1) {
						 * // posYheight -= 0.5f ; collide = true; isJump =
						 * false; } else { collide = false; isJump = true; }
						 * 
						 * if ((int) posYheight <= level.box[i].getA().y + 2.3f)
						 * { posYheight += 0.25f; collide = true; isJump =
						 * false; dontjump = false; countjump = 0; height = 0; }
						 * else { collide = false; isJump = true; }
						 * 
						 * if (posYheight < level.box[i].getC().y + 0.9f) {
						 * collide = true; posYheight -= 0.25f; } collide =
						 * false;
						 */
						if (posYheight <= level.boxx3[i].getA().y + 2.95f
								&& posYheight > level.boxx3[i].getA().y + 2) {
							posYheight = level.boxx3[i].getA().y + 2.95f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
							isJump = false;
							dontjump = false;
							countjump = 0;
							height = 0;
						} else {
							collide = false;
							isJump = true;
						}

						if (posYheight <= level.boxx3[i].getA().y - 1.0f
								&& posYheight > level.boxx3[i].getA().y - 1.35f) {
							posYheight = level.boxx3[i].getA().y - 1.35f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
						} else {
							collide = false;

						}

						if ((int) posfix.x >= level.boxx3[i].getA().x
								&& posYheight <= level.boxx3[i].getA().y + 2.5f
								&& posYheight >= level.boxx3[i].getC().y) {
							pos.x -= x;
						} else {

							if ((int) posfix.x <= level.boxx3[i].getB().x
									&& posYheight <= level.boxx3[i].getA().y + 2.5f
									&& posYheight >= level.boxx3[i].getC().y) {
								pos.x -= x;
							}
						}

						if ((int) posfix.z >= level.boxx3[i].getA().z
								&& posYheight <= level.boxx3[i].getA().y + 2.5f
								&& posYheight >= level.boxx3[i].getC().y) {
							pos.z -= z;
						} else {

							if ((int) posfix.z <= level.boxx3[i].getE().z
									&& posYheight <= level.boxx3[i].getA().y + 2.5f
									&& posYheight >= level.boxx3[i].getC().y) {
								pos.z -= z;
							}
						}
					}
				}
			} else
				collide = false;
		}
	}

	public void checkFallingBox3() {
		// collision checks for level falling cubes, also collision response
		for (int i = 0; i < level.fallingboxNumber; i++) {
			if (level.boxx2f[i].isVisible()) {
				if (posYheight >= level.boxx2f[i].getC().y - 1
						&& posYheight <= level.boxx2f[i].getA().y + 6) {
					if (posfix.x >= level.boxx2f[i].getA().x
							& posfix.x <= level.boxx2f[i].getB().x) {

						if (posfix.z > level.boxx2f[i].getA().z
								& posfix.z < level.boxx2f[i].getE().z) {
							/*
							 * if ((int) posYheight >= level.box2[i].getC().y -
							 * 1) { // posYheight -= 0.5f ; collide = true;
							 * isJump = true; } else { collide = false; isJump =
							 * true; }
							 * 
							 * if ((int) posYheight <= level.box2[i].getA().y +
							 * 2.7f) { level.box2[i].setAllvert(0, -0.25f, 0);
							 * 
							 * // posYheight += 0.25f; collide = true; isJump =
							 * false; dontjump = false; countjump = 0; height =
							 * 0; } else { collide = false; isJump = true; }
							 * 
							 * if (posYheight < level.box2[i].getC().y + 0.9f) {
							 * collide = true; posYheight -= 0.25f; } collide =
							 * false; isJump = true;
							 */

							if (posYheight <= level.boxx2f[i].getA().y + 2.95f
									&& posYheight > level.boxx2f[i].getA().y + 2) {
								// posYheight = level.box[i].getA().y + 2.95f;
								// posYheight += 0.05f;
								if (yVel > 0) {
									posYheight = level.boxx2f[i].getA().y + 2.95f;
								} else {
									level.boxx2f[i].setAllvert(0, yVel - 0.01f,
											0, 0);
								}

								collide = true;
								isJump = false;
								dontjump = false;
								countjump = 0;
								height = 0;
							} else {
								collide = false;
								isJump = true;
							}

							if (posYheight <= level.boxx2f[i].getA().y - 1.0f
									&& posYheight > level.boxx2f[i].getA().y - 1.35f) {
								posYheight = level.boxx2f[i].getA().y - 1.35f;
								// posYheight += 0.05f;

								yVel = 0;
								collide = true;
							} else {
								collide = false;

							}

							if ((int) posfix.x >= level.boxx2f[i].getA().x
									&& posYheight <= level.boxx2f[i].getA().y + 2
									&& posYheight >= level.boxx2f[i].getC().y) {
								pos.x -= x;
							} else {

								if ((int) posfix.x <= level.boxx2f[i].getB().x
										&& posYheight <= level.boxx2f[i].getA().y + 2
										&& posYheight >= level.boxx2f[i].getC().y) {
									pos.x -= x;
								}
							}

							if ((int) posfix.z >= level.boxx2f[i].getA().z
									&& posYheight <= level.boxx2f[i].getA().y + 2
									&& posYheight >= level.boxx2f[i].getC().y) {
								pos.z -= z;
							} else {

								if ((int) posfix.z <= level.boxx2f[i].getE().z
										&& posYheight <= level.boxx2f[i].getA().y + 2
										&& posYheight >= level.boxx2f[i].getC().y) {
									pos.z -= z;
								}
							}
						}
					}
				} else
					collide = false;
			}
		}
	}

	public void checkFallingBox() {
		// collision checks for level falling cubes, also collision response
		for (int i = 0; i < level.fallingboxNumber; i++) {
			if (level.box2[i].isVisible()) {
				if (posYheight >= level.box2[i].getC().y - 1
						&& posYheight <= level.box2[i].getA().y + 6) {
					if (posfix.x >= level.box2[i].getA().x
							& posfix.x <= level.box2[i].getB().x) {

						if (posfix.z > level.box2[i].getA().z
								& posfix.z < level.box2[i].getE().z) {
							/*
							 * if ((int) posYheight >= level.box2[i].getC().y -
							 * 1) { // posYheight -= 0.5f ; collide = true;
							 * isJump = true; } else { collide = false; isJump =
							 * true; }
							 * 
							 * if ((int) posYheight <= level.box2[i].getA().y +
							 * 2.7f) { level.box2[i].setAllvert(0, -0.25f, 0);
							 * 
							 * // posYheight += 0.25f; collide = true; isJump =
							 * false; dontjump = false; countjump = 0; height =
							 * 0; } else { collide = false; isJump = true; }
							 * 
							 * if (posYheight < level.box2[i].getC().y + 0.9f) {
							 * collide = true; posYheight -= 0.25f; } collide =
							 * false; isJump = true;
							 */

							if (posYheight <= level.box2[i].getA().y + 2.95f
									&& posYheight > level.box2[i].getA().y + 2) {
								// posYheight = level.box[i].getA().y + 2.95f;
								// posYheight += 0.05f;
								if (yVel > 0) {
									posYheight = level.box2[i].getA().y + 2.95f;
								} else {
									level.box2[i]
											.setAllvert(0, yVel - 0.01f, 0);
								}

								collide = true;
								isJump = false;
								dontjump = false;
								countjump = 0;
								height = 0;
							} else {
								collide = false;
								isJump = true;
							}

							if (posYheight <= level.box2[i].getA().y - 1.0f
									&& posYheight > level.box2[i].getA().y - 1.35f) {
								posYheight = level.box2[i].getA().y - 1.35f;
								// posYheight += 0.05f;

								yVel = 0;
								collide = true;
							} else {
								collide = false;

							}

							if ((int) posfix.x >= level.box2[i].getA().x
									&& posYheight <= level.box2[i].getA().y + 2
									&& posYheight >= level.box2[i].getC().y) {
								pos.x -= x;
							} else {

								if ((int) posfix.x <= level.box2[i].getB().x
										&& posYheight <= level.box2[i].getA().y + 2
										&& posYheight >= level.box2[i].getC().y) {
									pos.x -= x;
								}
							}

							if ((int) posfix.z >= level.box2[i].getA().z
									&& posYheight <= level.box2[i].getA().y + 2
									&& posYheight >= level.box2[i].getC().y) {
								pos.z -= z;
							} else {

								if ((int) posfix.z <= level.box2[i].getE().z
										&& posYheight <= level.box2[i].getA().y + 2
										&& posYheight >= level.box2[i].getC().y) {
									pos.z -= z;
								}
							}
						}
					}
				} else
					collide = false;
			}
		}
	}

	public void checkMovingBox() {
		for (int i = 0; i < level.movingboxNumber; i++) {
			if (posYheight >= level.box3[i].getC().y - 1
					&& posYheight <= level.box3[i].getA().y + 4) {
				if (posfix.x >= level.box3[i].getA().x
						& posfix.x <= level.box3[i].getB().x) {

					if (posfix.z > level.box3[i].getA().z
							& posfix.z < level.box3[i].getE().z) {
						/*
						 * if ((int) posYheight >= level.box3[i].getC().y - 1) {
						 * // posYheight -= 0.5f ; collide = true;
						 * 
						 * } else collide = false;
						 * 
						 * if ((int) posYheight <= level.box3[i].getA().y +
						 * 2.3f) { if (!side) { pos.x += 0.15f; } else { pos.x
						 * -= 0.15f; } posYheight += 0.25f; collide = true;
						 * isJump = false; dontjump = false; countjump = 0;
						 * height = 0; } else { collide = false; isJump = true;
						 * }
						 * 
						 * if (posYheight < level.box3[i].getC().y + 0.9f) {
						 * isJump = true; collide = true; posYheight -= 0.25f; }
						 * collide = false;
						 */

						if (posYheight <= level.box3[i].getA().y + 2.95f
								&& posYheight > level.box3[i].getA().y + 2) {
							posYheight = level.box3[i].getA().y + 2.95f;
							// posYheight += 0.05f;
							if (!side) {
								pos.x += 0.15f;
							} else {
								pos.x -= 0.15f;
							}
							yVel = 0;
							collide = true;
							isJump = false;
							dontjump = false;
							countjump = 0;
							height = 0;
						} else {
							collide = false;
							isJump = true;
						}

						if (posYheight <= level.box3[i].getA().y - 1.0f
								&& posYheight > level.box3[i].getA().y - 1.35f) {
							posYheight = level.box3[i].getA().y - 1.35f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
						} else {
							collide = false;

						}
						if ((int) posfix.x >= level.box3[i].getA().x
								&& posYheight <= level.box3[i].getA().y + 2.5f
								&& posYheight >= level.box3[i].getC().y) {
							if (!side) {
								pos.x += 0.15f;
							} else {
								pos.x -= 0.15f;
							}
							pos.x -= x;
						} else {

							if ((int) posfix.x <= level.box3[i].getB().x
									&& posYheight <= level.box3[i].getA().y + 2.5f
									&& posYheight >= level.box3[i].getC().y) {
								if (!side) {
									pos.x += 0.15f;
								} else {
									pos.x -= 0.15f;
								}
								pos.x -= x;
							}
						}

						if ((int) posfix.z >= level.box3[i].getA().z
								&& posYheight <= level.box3[i].getA().y + 2.5f
								&& posYheight >= level.box3[i].getC().y) {
							pos.z -= z;
						} else {

							if ((int) posfix.z <= level.box3[i].getE().z
									&& posYheight <= level.box3[i].getA().y + 2.5f
									&& posYheight >= level.box3[i].getC().y) {
								pos.z -= z;
							}
						}
					}
				}
			} else
				collide = false;
		}
	}

	public void checkMovingBox3() {
		for (int i = 0; i < level.movingboxNumber; i++) {
			if (level.boxx3m[i].isVisible()) {
			if (posYheight >= level.boxx3m[i].getC().y - 1
					&& posYheight <= level.boxx3m[i].getA().y + 4) {
				if (posfix.x >= level.boxx3m[i].getA().x
						& posfix.x <= level.boxx3m[i].getB().x) {

					if (posfix.z > level.boxx3m[i].getA().z
							& posfix.z < level.boxx3m[i].getE().z) {
						/*
						 * if ((int) posYheight >= level.box3[i].getC().y - 1) {
						 * // posYheight -= 0.5f ; collide = true;
						 * 
						 * } else collide = false;
						 * 
						 * if ((int) posYheight <= level.box3[i].getA().y +
						 * 2.3f) { if (!side) { pos.x += 0.15f; } else { pos.x
						 * -= 0.15f; } posYheight += 0.25f; collide = true;
						 * isJump = false; dontjump = false; countjump = 0;
						 * height = 0; } else { collide = false; isJump = true;
						 * }
						 * 
						 * if (posYheight < level.box3[i].getC().y + 0.9f) {
						 * isJump = true; collide = true; posYheight -= 0.25f; }
						 * collide = false;
						 */

						if (posYheight <= level.boxx3m[i].getA().y + 2.95f
								&& posYheight > level.boxx3m[i].getA().y + 2) {
							posYheight = level.boxx3m[i].getA().y + 2.95f;
							// posYheight += 0.05f;
							if (!side) {
								pos.x += 0.15f;
							} else {
								pos.x -= 0.15f;
							}
							yVel = 0;
							collide = true;
							isJump = false;
							dontjump = false;
							countjump = 0;
							height = 0;
						} else {
							collide = false;
							isJump = true;
						}

						if (posYheight <= level.boxx3m[i].getA().y - 1.0f
								&& posYheight > level.boxx3m[i].getA().y - 1.35f) {
							posYheight = level.boxx3m[i].getA().y - 1.35f;
							// posYheight += 0.05f;

							yVel = 0;
							collide = true;
						} else {
							collide = false;

						}
						if ((int) posfix.x >= level.boxx3m[i].getA().x
								&& posYheight <= level.boxx3m[i].getA().y + 2.5f
								&& posYheight >= level.boxx3m[i].getC().y) {
							if (!side) {
								pos.x += 0.15f;
							} else {
								pos.x -= 0.15f;
							}
							pos.x -= x;
						} else {

							if ((int) posfix.x <= level.boxx3m[i].getB().x
									&& posYheight <= level.boxx3m[i].getA().y + 2.5f
									&& posYheight >= level.boxx3m[i].getC().y) {
								if (!side) {
									pos.x += 0.15f;
								} else {
									pos.x -= 0.15f;
								}
								pos.x -= x;
							}
						}

						if ((int) posfix.z >= level.boxx3m[i].getA().z
								&& posYheight <= level.boxx3m[i].getA().y + 2.5f
								&& posYheight >= level.boxx3m[i].getC().y) {
							pos.z -= z;
						} else {

							if ((int) posfix.z <= level.boxx3m[i].getE().z
									&& posYheight <= level.boxx3m[i].getA().y + 2.5f
									&& posYheight >= level.boxx3m[i].getC().y) {
								pos.z -= z;
							}
						}
					}
				}
			} else
				collide = false;
		}
		}
	}

	public void checkCollisions() {
		// check for collisions (wall and box)

		// Wall checkers
		if ((int) pos.x == 19)
			pos.x = 19;

		if ((int) pos.x == -19)
			pos.x = -19;

		if ((int) pos.z == 19)
			pos.z = 19;

		if ((int) pos.z == -19 &&  pos.x <= -6 | pos.x >= 6)
			pos.z = -19;
		
		if ((int) pos.z == -19 && posYheight > 7)
			pos.z = -19;
		
		if ((int) pos.z == -28 )
			pos.z = -28;
		
		if ((int) pos.z < -19 & (int)pos.x == -5)
			pos.x = -5;
		
		if ((int) pos.z < -19 & (int)pos.x == 5)
			pos.x = 5;
		
		if ((int) pos.x == -10 & pos.z >= -19 & pos.z <= -11) {
			pos.x = -10;
		}else {
			if ((int) pos.z == -10 & pos.x >= -19 & pos.x < -11) {
				pos.z = -10;
			}else {
				if ((int) pos.x == 10 & pos.z <= 19 & pos.z >= 11) {
					pos.x = 10;
				}else {
					if ((int) pos.z == 10 & pos.x <= 19 & pos.x > 11) {
						pos.z = 10;
					}else {
						if ((int) pos.z == -10 & pos.x <= 19 & pos.x > 11) {
							pos.z = -10;
						}else {
							if ((int) pos.x == 10 & pos.z >= -19 & pos.z <= -11) {
								pos.x = 10;
							}else {
								if ((int) pos.z == 10 & pos.x >= -19 & pos.x < -11) {
									pos.z = 10;
								}else {
									if ((int) pos.x == -10 & pos.z <= 19 & pos.z >= 11) {
										pos.x = -10;
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		
		if (pos.z <=  -19) {
			jumptimes = 1;
			maxHeight = 7;
			if (yVel > 0.2f) 
				yVel = 0.1f;
		}
		else  {
			jumptimes = 2;
			maxHeight = 30;
		}
		
		
		
		

		// checkFallingBox(); //for old rendering
		checkFallingBox3();
		// checkNormalBox();
		// checkMovingBox();
		checkMovingBox3();
		// checkNormalBoxx();
		checkNormalBox3();
		/*
		 * // for old moving box immediate rendering for (int i = 0; i <
		 * level.movingboxNumber; i++) { if (!side) {
		 * level.box3[i].setAllvert(-0.15f, 0, 0);
		 * 
		 * } else { level.box3[i].setAllvert(+0.15f, 0, 0); } }
		 */
		// move moving box
		for (int i = 0; i < level.movingboxNumber; i++) {
			if (!side) {
				level.boxx3m[i].setAllvert(-0.15f, 0, 0, 0);

			} else {
				level.boxx3m[i].setAllvert(+0.15f, 0, 0, 0);
			}
		}

		// count moving box moves
		++count;
		if (count == 40) {
			if (!side) {
				side = true;
				count = 0;
			} else {
				side = false;
				count = 0;
			}
		}

		if (!side) {
			// boxX -= 0.15f;
		} else {
			// boxX += 0.15f;
		}
		// old physics settings
		/*
		 * jump test // don't fall when touching floor, and clear height counter
		 * if (posYheight <= 0.2f) { dontjump = false; isJump = false; countjump
		 * = 0; height = 0; posYheight = 0.2f; if (posYheight <= 0.0f &&
		 * Keyboard.isKeyDown(Keyboard.KEY_SPACE)) { posYheight = 0.7f;
		 * posYheight -= 0.5f; } } // if player not collide posYheight will drop
		 * = gravity if (!collide) { posYheight -= 0.25f; }
		 */

		// new physics
		yVel = yVel - yAcc * timeElapsed;

		if (posYheight <= 0.00f) {
			dontjump = false;
			isJump = false;
			countjump = 0;
			height = 0;
			yVel = 0;
			posYheight = 0.00f;
		} else {
			posYheight = posYheight + yVel;

		}

		if (collide) {
			yVel = 0;
		}

		// collision for sprites
		for (int i = 0; i < (level.boxNumber / 10); i++) {
			if (level.sprite[i].x >= -pos.x - 0.5f
					&& level.sprite[i].x < -pos.x + 0.5f) {
				if (level.sprite[i].y >= posYheight - 2.5f
						&& level.sprite[i].y < posYheight) {
					if (level.sprite[i].z >= -pos.z - 0.5f
							&& level.sprite[i].z < -pos.z + 0.5f) {
						if (level.sprite[i].isVisible()) {

							level.sprite[i].setVisible(false);
							collect = true;
							frame = getTime();
							k = random.nextInt(3);
							++spritecounter;
						}

					}

				}
			}

		}

		if (collect) {
			if (frame - getTime() < -5000) {
				l = 0;
				collect = false;
			}
		}

	}

	public static void main(String[] args) {
		Main m = new Main();
		m.init();
		m.start();
	}
}