package objects;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.constants.ANI_SPEED;
import static utilz.constants.Laser.LASER_HEIGHT;
import static utilz.constants.Laser.LASER_WIDTH;

public class Laser extends GameObject {
    private BufferedImage[] animations;
    private int xOffset = (int) (8 * Game.SCALE);
    private int yOffset = (int) (23*1.5 * Game.SCALE);

    public Laser(int x, int y) {
        super(x, y, 123);
        hitbox = new Rectangle2D.Float(x+29, y+13, LASER_WIDTH, (int)(23*1.5*Game.SCALE));
        this.active = false;
        loadAnimations();
    }

    private void loadAnimations() {
        try {
            animations = new BufferedImage[14];
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/laser.png"));
            int width = image.getWidth();
            int height = 100;
            for (int i = 0; i < animations.length; i++) {
                animations[i] = image.getSubimage(0, i * height, width, height);
            }
            for (int i = 14; i < 20; i++) {
                animations[i] = animations[i-6];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations.length) {
                aniIndex = 0;
                active = false;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(animations[aniIndex], (int) (this.hitbox.x - xOffset)-xLvlOffset+LASER_WIDTH, (int) (this.hitbox.y - yOffset), LASER_WIDTH* -1, LASER_HEIGHT, null);
//        drawHitbox(g, xLvlOffset);
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void changeHitbox(int y){
        hitbox.y = y;
    }
}
