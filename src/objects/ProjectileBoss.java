package objects;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static utilz.LoadSave.GetSpriteAtlas;
import static utilz.constants.FinalBossConstants.*;

public class ProjectileBoss extends GameObject {
    private Rectangle2D.Float hitbox;
    private BufferedImage[] animation;

    public ProjectileBoss(int x, int y) {
        super(x, y, 123);
        yDrawOffset = (int) (30*3*Game.SCALE);
        xDrawOffset = (int) (8*3*Game.SCALE);
        hitbox = new Rectangle2D.Float(x, y, (int)(35*3*Game.SCALE), (int)(14*3*Game.SCALE));

        loadImgs();
    }

    private void loadImgs() {
        try {
            animation = new BufferedImage[6];
            int index = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    animation[index++] = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/projectiles.png")).getSubimage(j * 100, i * 100, 100, 100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        updateAnimationTick();
        updatePos();
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(animation[aniIndex], (int) (this.hitbox.x-xDrawOffset) + PROJECTILE_WIDTH - xLvlOffset, (int) (this.hitbox.y-yDrawOffset), PROJECTILE_WIDTH * -1, PROJECTILE_HEIGHT, null);
//            drawHitbox(g, xLvlOffset);
        }
    }

    private void updatePos() {
        hitbox.x -= SPEED;
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


}
