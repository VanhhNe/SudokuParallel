package Helper;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class HoverButton extends JButton {
	float alpha = 0.5f;
	ML mouseL;

	public HoverButton(String text) {
		super(text);
		setFocusPainted(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		mouseL = new ML(this);
		addMouseListener(mouseL);
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		super.paintComponent(g2);
	}
}
