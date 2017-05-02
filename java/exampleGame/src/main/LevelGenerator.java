package main;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class LevelGenerator {
	// class that generate Box objects

	public int boxNumber;
	public int fallingboxNumber;
	public int movingboxNumber;
	public Box[] box;
	public Box[] box2;
	public Box[] box3;
	
	public Box3[] boxx3;
	public Box3[] boxx3m;
	public Box3[] boxx2f;

	public Boxx[] boxx;

	//brick way
	/*
	String textureloc = "res/BrickOldSharp.jpg";
	String textureloc2 = "res/BrickOlddirt.jpg";
	String textureloc3 = "res/BrickRound.jpg";
	*/
	
	//wood way
	String textureloc = "res/WoodPlanksBare.jpg";
	String textureloc2 = "res/WoodPlanksFloors.jpg";
	String textureloc3 = "res/WoodRoug.jpg";
	
	
	Random random = new Random();
	Random random2 = new Random();
	int displaylist1;
	int displaylist2;
	int displaylist3;
	Sprite[] sprite;
	float[] floatArray;
	int texture1;
	int texture2;
	int texture3;

	public LevelGenerator(int boxNumber, int fallingboxNumber,
			int movingboxNumber) {
		this.boxNumber = boxNumber;
		this.fallingboxNumber = fallingboxNumber;
		this.movingboxNumber = movingboxNumber;

		// generateNormalBox();
		//generateFallingBox();
		generateFallingBox3();
		//generateMovingBox();
		// generateNormalBlock();
		//generateFallingBlock();
		//generateMovingBlock();
		//generateNormalBoxx();
		generateNormalBox3();
		generateMovingBox3();

		BufferedImage image = TextureLoader.loadImage(textureloc);
		texture1 = TextureLoader.loadTexture(image);
		BufferedImage image2 = TextureLoader.loadImage(textureloc2);
		texture2 = TextureLoader.loadTexture(image2);
		BufferedImage image3 = TextureLoader.loadImage(textureloc3);
		texture3 = TextureLoader.loadTexture(image3);
	}

	public void generateNormalBox() {
		// initiate Box

		box = new Box[boxNumber];
		sprite = new Sprite[(boxNumber / 10) + 1];

		for (int i = 0; i < boxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(16); // random value for Vector
			int z = random.nextInt(16);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int g = i * 2;

			sprite[i / 10] = new Sprite(x - 2, g + 1, z - 2);

			if (i % 2 == 0) {
				box[i] = new Box(new Vector3f(x - 4, g, z - 4), new Vector3f(x,
						g, z - 4), new Vector3f(x, g - 0.7f, z - 4),
						new Vector3f(x - 4, g - 0.7f, z - 4), new Vector3f(
								x - 4, g, z), new Vector3f(x, g, z),
						new Vector3f(x, g - 0.7f, z), new Vector3f(x - 4,
								g - 0.7f, z), textureloc);

			} else {
				box[i] = new Box(new Vector3f(x - 4, g, z - 4), new Vector3f(x,
						g, z - 4), new Vector3f(x, g - 0.5f, z - 4),
						new Vector3f(x - 4, g - 0.5f, z - 4), new Vector3f(
								x - 4, g, z), new Vector3f(x, g, z),
						new Vector3f(x, g - 0.5f, z), new Vector3f(x - 4,
								g - 0.5f, z), textureloc2);

			}

		}

	}

	public void generateNormalBoxx() {
		// initiate Box

		boxx = new Boxx[boxNumber];
		sprite = new Sprite[(boxNumber / 10) + 1];

		for (int i = 0; i < boxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(16); // random value for Vector
			int z = random.nextInt(16);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int g = i * 2;

			sprite[i / 10] = new Sprite(x - 2, g + 1, z - 2);

			boxx[i] = new Boxx(new Vector3f(x - 4, g, z - 4), new Vector3f(x,
					g, z - 4), new Vector3f(x, g - 0.5f, z - 4), new Vector3f(
					x - 4, g - 0.5f, z - 4), new Vector3f(x - 4, g, z),
					new Vector3f(x, g, z), new Vector3f(x, g - 0.5f, z),
					new Vector3f(x - 4, g - 0.5f, z));

		}

	}
	

	public void generateNormalBox3() {
		// initiate Box

		boxx3 = new Box3[boxNumber];
		sprite = new Sprite[(boxNumber / 10) + 1];

		for (int i = 0; i < boxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(12); // random value for Vector
			int z = random.nextInt(12);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int g = i * 2;

			sprite[i / 10] = new Sprite(x - 2, g + 1, z - 2);

			boxx3[i] = new Box3(x, g, z,new Vector3f(x - 4, g, z - 4), new Vector3f(x,
					g, z - 4), new Vector3f(x, g - 0.5f, z - 4), new Vector3f(
					x - 4, g - 0.5f, z - 4), new Vector3f(x - 4, g, z),
					new Vector3f(x, g, z), new Vector3f(x, g - 0.5f, z),
					new Vector3f(x - 4, g - 0.5f, z));

		}

	}
	

	public void generateMovingBox3() {
		// initiate Box

		boxx3m = new Box3[movingboxNumber];

		for (int i = 0; i < movingboxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(11); // random value for Vector
			int z = random.nextInt(11);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int h = ((boxNumber * 2) / movingboxNumber) * i;

			// System.out.println(x);
			
				boxx3m[i] = new Box3(x, h, z,new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.7f, z - 4),
						new Vector3f(x - 4, h - 0.7f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.7f, z), new Vector3f(x - 4,
								h - 0.7f, z));
			

		}

	}


	public void generateFallingBox() {
		// initiate Box

		box2 = new Box[fallingboxNumber];

		for (int i = 0; i < fallingboxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(5); // random value for Vector
			int z = random.nextInt(5);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int h = ((boxNumber * 2) / fallingboxNumber) * i;

			// System.out.println(x);
			if (i % 2 == 0) {
				box2[i] = new Box(new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.7f, z - 4),
						new Vector3f(x - 4, h - 0.7f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.7f, z), new Vector3f(x - 4,
								h - 0.7f, z), textureloc3);
			} else {
				box2[i] = new Box(new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.5f, z - 4),
						new Vector3f(x - 4, h - 0.5f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.5f, z), new Vector3f(x - 4,
								h - 0.5f, z), textureloc3);
			}

		}

	}
	

	public void generateFallingBox3() {
		// initiate Box

		boxx2f = new Box3[fallingboxNumber];

		for (int i = 0; i < fallingboxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(8); // random value for Vector
			int z = random.nextInt(8);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int h = ((boxNumber * 2) / fallingboxNumber) * i;

			// System.out.println(x);
			
				boxx2f[i] = new Box3(x, h, z,new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.7f, z - 4),
						new Vector3f(x - 4, h - 0.7f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.7f, z), new Vector3f(x - 4,
								h - 0.7f, z));
			
		}

	}

	public void generateMovingBox() {
		// initiate Box

		box3 = new Box[movingboxNumber];

		for (int i = 0; i < movingboxNumber; i++) {
			// loop to create random coordinates
			int x = random.nextInt(16); // random value for Vector
			int z = random.nextInt(16);
			int k = random2.nextInt(2); // values to also set random - values
			int l = random2.nextInt(2);
			if (k == 0)
				x = -x;

			if (l == 0)
				z = -z;

			int h = ((boxNumber * 2) / movingboxNumber) * i;

			// System.out.println(x);
			if (i % 2 == 0) {
				box3[i] = new Box(new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.7f, z - 4),
						new Vector3f(x - 4, h - 0.7f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.7f, z), new Vector3f(x - 4,
								h - 0.7f, z), textureloc);
			} else {
				box3[i] = new Box(new Vector3f(x - 4, h, z - 4), new Vector3f(
						x, h, z - 4), new Vector3f(x, h - 0.5f, z - 4),
						new Vector3f(x - 4, h - 0.5f, z - 4), new Vector3f(
								x - 4, h, z), new Vector3f(x, h, z),
						new Vector3f(x, h - 0.5f, z), new Vector3f(x - 4,
								h - 0.5f, z), textureloc2);
			}

		}

	}

	public void generateNormalBlock() {
		// build cubes

		displaylist1 = glGenLists(1);
		glNewList(displaylist1, GL_COMPILE);
		for (int i = 0; i < boxNumber; i++) {
			box[i].build();
		}

		glEndList();

	}

	public void generateSprites() {

		for (int i = 0; i < (boxNumber / 10); i++) {

			if (sprite[i].isVisible() == true)
				sprite[i].render();
		}
	}

	public void generateMovingBlock() {
		// build cubes

		displaylist3 = glGenLists(1);
		glNewList(displaylist3, GL_COMPILE);

		for (int i = 0; i < movingboxNumber; i++) {

			if (box3[i].isVisible() == true)
				box3[i].build();

		}

		glEndList();

	}
	
	public void generateMovingBlock3a() {
		// build cubes


		for (int i = 0; i < movingboxNumber; i++) {

			if (i % 2 != 0) {
				boxx3m[0].setVisible(false);
				if (boxx3m[i].isVisible() == true)
					boxx3m[i].render();
				//boxx3m[i].render();
			}
		}

	

	}
	
	public void generateMovingBlock3b() {
		// build cubes


		for (int i = 0; i < movingboxNumber; i++) {

			if (i % 2 == 0) {
				boxx3m[0].setVisible(false);
				if (boxx3m[i].isVisible() == true)
					boxx3m[i].render();
				//boxx3m[i].render();
			}
		}

	

	}

	public void generateFallingBlock3() {

		// build cubes

		for (int i = 0; i < fallingboxNumber; i++) {
			boxx2f[0].setVisible(false);
			if (boxx2f[i].isVisible() == true)
				boxx2f[i].render();
			
		}

	}

	public void generateFallingBlock() {

		// build cubes

		for (int i = 0; i < fallingboxNumber; i++) {
			box2[0].setVisible(false);
			if (box2[i].isVisible() == true)
				box2[i].build();
		}

	}

	public void callNormalBlock() {
		glCallList(displaylist1);

	}

	public void rNormalBlock() {
		for (int i = 0; i < boxNumber; i++) {
			if (i % 2 == 0) {
				boxx[i].render();
			}
		}
	}

	public void rNormalBlock2() {
		for (int i = 0; i < boxNumber; i++) {
			if (i % 2 != 0) {
				boxx[i].render();
			}
		}
	}
	
	public void rNormalBlock3() {
		for (int i = 0; i < boxNumber; i++) {
			if (i % 2 != 0) {
				boxx3[i].render();
			}
		}
	}
	
	public void rNormalBlock4() {
		for (int i = 0; i < boxNumber; i++) {
			if (i % 2 == 0) {
				boxx3[i].render();
			}
		}
	}
	
	
	public void callFallingBlock() {
		glCallList(displaylist2);
	}

	public void callMovingBlock() {
		glCallList(displaylist3);
	}

	public void destroy() {
		glDeleteLists(displaylist1, 1); // removing display list
		glDeleteLists(displaylist2, 1); // removing display list
		glDeleteLists(displaylist3, 1); // removing display list

		//for (int i = 0; i < boxNumber; i++) {

			//box[i].destroy();

		//}
		//for (int i = 0; i < fallingboxNumber; i++) {

		//	box2[i].destroy();
		//}

		//for (int i = 0; i < movingboxNumber; i++) {

		//	box3[i].destroy();

		//}

		//for (int i = 0; i < boxNumber; i++) {
			//boxx[i].destroy();
		//}
		for (int i = 0; i < boxNumber; i++) {
			boxx3[i].destroy();
		}
		glDeleteTextures(texture1);
		glDeleteTextures(texture2);
	}

}
