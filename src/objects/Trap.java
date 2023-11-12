package objects;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.constants.FinalBossConstants.*;
import static utilz.constants.ObjectConstants.GetSpriteAmount;

public class Trap extends GameObject {

    private BufferedImage[] image;
    private Rectangle2D.Float hitbox;
    private boolean canDamage;
    private boolean ok = true;

    public Trap(int x, int y, int objType) {
        super(x, y, objType);
        hitbox = new Rectangle2D.Float(x, y, TRAP_WIDTH, TRAP_HEIGHT);
        this.active = true;
        this.canDamage = false;
        loadImages();
    }

    private void loadImages() {
        this.image = new BufferedImage[15];
        try {
            BufferedImage tmp = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/trap.png"));
            for (int i = 0; i < 6; i++) {
                image[i] = tmp.getSubimage(i * 16, 0, 16, 32);
            }
            for(int i=6;i<9;i++){
                image[i]=image[5];
            }
            for (int i = 9; i < 14; i++) {
                image[i] = image[14 - i];
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
        if (aniTick >= 20) {
            aniTick = 0;
            aniIndex++;
            if (ok && aniIndex >= 2) {
                this.canDamage = true;
                ok = false;
            }
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                this.active = false;
                ok = true;
                this.canDamage = false;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(image[aniIndex], (int) (this.hitbox.x - xLvlOffset), (int) (this.hitbox.y), TRAP_WIDTH, TRAP_HEIGHT, null);
        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setCanDamage(boolean canDamage) {
        this.canDamage = canDamage;
    }

    public boolean isCanDamage() {
        return canDamage;
    }
}
