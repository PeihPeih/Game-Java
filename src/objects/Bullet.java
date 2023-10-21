package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

import static utilz.constants.Bullet.*;

public class Bullet extends GameObject {
    private Rectangle2D.Float hitbox;
    private int dir;
    private BufferedImage[] animation;
    private BufferedImage[] animation_dis;

    public Bullet(int x, int y, int dir, int objType) {
        super(x, y, objType);
        int xOffset = (int) (-5 * Game.SCALE);
        int yOffset = (int) (2 * Game.SCALE);

        if (dir == 1)
            xOffset = (int) (13 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, BULLET_WIDTH, BULLET_HEIGHT);
        this.dir = dir;
        loadImgs();

    }

    private void loadImgs() {
        animation = LoadSave.GetBulletAnimation();
        animation_dis = LoadSave.GetBulletDisappearAnimation();
    }

    public void update() {
        updateAnimationTick();
        updatePos();
    }

    public void draw(Graphics g, int xLvlOffset) {
        int flipX;
        if (dir == 1) flipX = 0;
        else flipX = BULLET_WIDTH;

        int flipW = dir;

        reset();
        if (active) {
            g.drawImage(animation[aniIndex], (int) (this.hitbox.x - xDrawOffset - xLvlOffset) + flipX, (int) (this.hitbox.y - yDrawOffset), BULLET_WIDTH * flipW, BULLET_HEIGHT, null);
//            drawHitbox(g, xLvlOffset);
        } else {
            g.drawImage(animation_dis[aniIndex], (int) (this.hitbox.x - 8 - xLvlOffset) + flipX, (int) (this.hitbox.y - yDrawOffset), BULLET_WIDTH * flipW, BULLET_HEIGHT, null);

        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    private void updatePos() {
        hitbox.x += dir * SPEED;
        if (Math.abs(hitbox.x - x) >= (int) (0.7 * Game.GAME_WIDTH)) {
            this.active = false;
        }
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


}
