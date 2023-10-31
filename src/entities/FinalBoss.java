package entities;

import main.Game;
import objects.ProjectileBoss;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.Direction.LEFT;
import static utilz.constants.FinalBossConstants.*;

public class FinalBoss extends Entity{
    private BufferedImage[][] animations;
    private int maxHeath;
    private int currentHealth;
    private int aniIndex, enemyState;
    private int aniTick, aniSpeed = 12;
    private ArrayList<ProjectileBoss> projectiles;

    public FinalBoss(float x, float y, int width, int height) {
        super(x,y,width,height);
        loadsAnimation();
        initHitbox(x, y, width,height);
        this.maxHeath = 1500;
        this.currentHealth = maxHeath;
        this.enemyState = IDLE;
        this.projectiles = new ArrayList<>();
    }

    /* Code ảnh ở đây
    Mỗi hàng ảnh là 100 pixel
    Mỗi ảnh trong 1 hàng là 100 pixel
    => Một hoạt ảnh có size 100x100
    */
    private void loadsAnimation() {
        this.animations = new BufferedImage[7][14];
    }

    public void setState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void update(){
        updateAnimationTicks();
    }

    private void updateAnimationTicks() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyState)) {
                aniIndex = 0;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(animations[enemyState][aniIndex], (int) (hitbox.x - xLvlOffset ), (int) (hitbox.y), width , height, null);
        // Ve hitbox cho nhan vat (xoa di khi game hoan thanh)
//        drawHitbox(g, xLvlOffset);
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
//            setState(DEAD);
        }
    }
}
