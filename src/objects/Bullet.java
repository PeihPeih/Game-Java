package objects;

import main.Game;
import utilz.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bullet extends Object {
    private BufferedImage[] animations;

    public Bullet(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitbox(x, y, (int) (15 * Game.SCALE), (int) (11 * Game.SCALE));
        loadAnimations();
    }

    private void loadAnimations(){
        animations = LoadSave.GetBulletAnimation();
    }
}
