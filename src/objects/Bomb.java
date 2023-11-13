package objects;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.constants.ANI_SPEED;
import static utilz.constants.Bullet.BULLET_HEIGHT;
import static utilz.constants.Bullet.BULLET_WIDTH;
import static utilz.constants.ObjectConstants.*;
import static utilz.constants.PlayerConstants.GetSpriteAmount;

public class Bomb extends GameObject {
    private float fallSpeed = 1.5f;

    private BufferedImage image;
    private BufferedImage[] explosion;
    private Rectangle2D.Float hitbox;

    private boolean isDestroy;

    public Bomb(int x, int y, int objType) {
        super(x, y, objType);

        int xOffset = (int) (10 * Game.SCALE);
        int yOffset = (int) (8 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, BOMB_WIDTH, BOMB_HEIGHT);
        image = LoadSave.GetSpriteAtlas(LoadSave.BOMB);
        explosion = LoadSave.GetExplosion();

        this.isDestroy = false;
    }

    public void update() {
        hitbox.y += fallSpeed;
        if (hitbox.y + hitbox.height > Game.GAME_HEIGHT + 100) active = false;
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (!isDestroy) {
            g.drawImage(image, (int) (this.hitbox.x - xDrawOffset - xLvlOffset), (int) (this.hitbox.y - yDrawOffset), BOMB_WIDTH, BOMB_HEIGHT, null);

//            drawHitbox(g,xLvlOffset);

        } else {
            destroy(g, xLvlOffset);
        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
    	
        isDestroy = destroy;
    }

    private void destroy(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(explosion[aniIndex], (int) (this.hitbox.x - xDrawOffset - xLvlOffset - BOMB_WIDTH), (int) (this.hitbox.y - yDrawOffset) - Game.TILES_SIZE, BOMB_WIDTH * 3, BOMB_HEIGHT * 3, null);
            aniIndex++;
            if (aniIndex == explosion.length) active = false;
        }
    }
}
