package entities;

import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.constants.Direction.*;
import static utilz.constants.PlayerConstants.*;

public abstract class Entity {
    protected float x, y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;


    public 	Entity(float x, float y, int width, int height) {
        this.x = x; // vị tri x ban dau
        this.y = y; // vi tri y ban dau
        this.height = height;
        this.width = width;

    }
    
    // Tao hitbox cho vat the
	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
		
	}
	// Ve hitbox de fix loi

	protected void drawHitbox(Graphics g,int xLvlOffset)
	{
		g.setColor(Color.GREEN);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}
	
	// ve hitbox attack
//	protected void drawAttackHitbox(Graphics g,int xLvlOffset)
//	{
//		g.setColor(Color.PINK);
//		g.drawRect((int) hitbox.x - xLvlOffset - 60, (int) hitbox.y, (int) hitbox.width * 4, (int) hitbox.height);
//	}

	
	public Rectangle2D.Float getHitbox()
	{
		return hitbox;
	}

}
