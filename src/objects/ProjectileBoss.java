package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static utilz.constants.Projectile.*;

public class ProjectileBoss extends GameObject {
    private Rectangle2D.Float hitbox;
    private BufferedImage image;

    public ProjectileBoss(int x, int y, int objType) {
        super(x, y, objType);
        hitbox = new Rectangle2D.Float(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        loadImgs();
    }

    private void loadImgs() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/projectile.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update() {
        updateAnimationTick();
        updatePos();
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(image, (int) (this.hitbox.x - xLvlOffset), (int) (this.hitbox.y), PROJECTILE_WIDTH, PROJECTILE_HEIGHT, null);
//            drawHitbox(g, xLvlOffset);
        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    private void updatePos() {
        hitbox.x += SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


}
