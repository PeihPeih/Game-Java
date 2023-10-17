package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Object {
    protected float x, y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;

    public Object(float x, float y, int width, int height) {
        this.x = x; // vị tri x ban dau
        this.y = y; // vi tri y ban dau
        this.height = height;
        this.width = width;

    }

    // Tao hitbox cho vat the
    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);

    }

    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }

    public void update(){

    }

    public void render(Graphics g, int xLvlOffset){
        
    }
}
