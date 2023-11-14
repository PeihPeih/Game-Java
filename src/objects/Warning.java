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
            for (int i = 0; i < 5; i++) {
                image[i] = tmp.getSubimage(32*i,0,32,32);
            }
            for(int i=5;i<9;i++){
                image[i]=image[9-i];
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
            g.drawImage(image[aniIndex], (int) (this.hitbox.x - xLvlOffset), (int) (this.hitbox.y) + Game.TILES_SIZE - 32, 32, 32, null);
        }
    }
}
