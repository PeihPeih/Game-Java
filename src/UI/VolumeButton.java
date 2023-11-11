package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.constants.UI.VolumeButtons.*;

public class VolumeButton extends PasuedButtons {

	private BufferedImage[] Imgs;
	private BufferedImage Slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0f;

	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VOLUME_WIDTH, height);// de x = width/2 de luc moi in ra no o gia thanh slider
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH / 2;// khoang cach toi thieu di chuyen sang trai
		maxX = x + width - VOLUME_WIDTH / 2;// khoang cach toi da di chuyen sang phai
		loadImgs();
	}

	private void loadImgs() {// load anh tu slider
		Imgs = new BufferedImage[3];
		for (int i = 0; i < Imgs.length; i++) {
			try {
				BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/UI/Option/Slider.png"));
				// Cat anh voi kich thuoc
				
				Imgs[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/UI/Option/Slider.png"));
			Slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public void draw(Graphics g) {
		// ve
		g.drawImage(Slider, x, y, width, height, null);
		g.drawImage(Imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);

	}

	public void ChangeX(int x) {
		// keo chinh sua volume
		if (x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		updateFloatValue();
		bounds.x = buttonX - VOLUME_WIDTH / 2;
	}
	
	private void updateFloatValue() {
		float range = maxX - minX;
		float value = buttonX - minX;
		floatValue = value / range;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public float getFloatValue() {
		return floatValue;
	}
}
