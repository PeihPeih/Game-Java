package objects;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.constants.FinalBossConstants.*;
import static utilz.constants.ObjectConstants.GetSpriteAmount;

public class Warning extends GameObject {

    private BufferedImage[] image;

    public Warning(int x, int y, int objType) {
        super(x, y, objType);
        this.active = false;
        initHitbox(TRAP_HEIGHT, TRAP_HEIGHT);
        loadImages();
    }

    private void loadImages() {
        this.image = new BufferedImage[10];
        try {
            BufferedImage tmp = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/warning.png"));
            for (int i = 0; i < 10; i++) {
                image[i] = tmp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (active) {
            updateAnimation();
        }
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= 25) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 10) {
                aniIndex = 0;
                this.active = false;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(image[aniIndex], (int) (this.hitbox.x - xLvlOffset), (int) (this.hitbox.y) + Game.TILES_SIZE - 36, 36, 36, null);
        }
    }
}
