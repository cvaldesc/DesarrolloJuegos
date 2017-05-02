package main;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class UserInterface extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7680398893662127395L;
	public static BufferedImage buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
	
	private static Font monoFont = new Font("Monospaced", Font.BOLD
			| Font.ITALIC, 36);

	public UserInterface() {
		
	    //setBackground(new java.awt.Color(0, 0, 0, 0));
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D) buffer.getGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g2.clearRect(0, 0, 400, 400);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		g2.setFont(monoFont);
		g2.drawString("Java Source", 50, 50);
		g2.dispose();
		Graphics2D g3 = (Graphics2D) g;
        g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC)); // only draws source, effectively clearing old image
        g3.drawImage(buffer, 0, 0, null);
        g3.dispose();
		
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

}
