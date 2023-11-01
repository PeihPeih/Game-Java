package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static utilz.constants.FinalBossConstants.*;

public class ProjectileBoss extends GameObject {
    private Rectangle2D.Float hitbox;
    private BufferedImage image;

    public ProjectileBoss(int x, int y) {
        super(x, y, 123);
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
            g.drawImage(image, (int) (this.hitbox.x)+PROJECTILE_WIDTH-xLvlOffset, (int) (this.hitbox.y), PROJECTILE_WIDTH*-1, PROJECTILE_HEIGHT, null);
        }
    }
    private void updatePos() {
        hitbox.x -= SPEED;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


}
