package Helper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ML extends MouseAdapter implements MouseListener {
	HoverButton button;

	public ML(HoverButton button) {
		this.button = button;
	}

	public void mouseExited(MouseEvent me) {
		new Thread(new Runnable() {
			public void run() {
				for (float i = 1f; i >= .5f; i -= .03f) {
					button.setAlpha(i);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}

	public void mouseEntered(MouseEvent me) {
		new Thread(new Runnable() {
			public void run() {
				for (float i = .5f; i <= 1f; i += .03f) {
					button.setAlpha(i);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}

	public void mousePressed(MouseEvent me) {
		new Thread(new Runnable() {
			public void run() {
				for (float i = 1f; i >= 0.6f; i -= .1f) {
					button.setAlpha(i);
					try {
						Thread.sleep(1);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
}
