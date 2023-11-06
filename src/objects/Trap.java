package objects;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.constants.ANI_SPEED;
import static utilz.constants.FinalBossConstants.*;
import static utilz.constants.ObjectConstants.*;
import static utilz.constants.ObjectConstants.GetSpriteAmount;

public class Trap extends GameObject {

    private BufferedImage[] image;
    private Rectangle2D.Float hitbox;


    public Trap(int x, int y, int objType) {
        super(x, y, objType);
        hitbox = new Rectangle2D.Float(x, y, TRAP_WIDTH, TRAP_HEIGHT);
        this.active = true;
        loadImages();
    }

    private void loadImages() {
        this.image = new BufferedImage[15];
        try {
            BufferedImage tmp = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/trap.png"));
            for (int i = 0; i < 6; i++) {
                image[i] = tmp.getSubimage(i * 16, 0, 16, 32);
            }
            for (int i = 6; i < 10; i++) {
                image[i]=image[5];
            }
            for(int i=10;i<15;i++){
                image[i]=image[15-i];
            }

            System.out.println(1);
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
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                this.active = false;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(image[aniIndex], (int) (this.hitbox.x - xLvlOffset), (int) (this.hitbox.y), TRAP_WIDTH, TRAP_HEIGHT, null);
//            drawHitbox(g, xLvlOffset);
        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
