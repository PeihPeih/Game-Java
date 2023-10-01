package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.constants.Direction.*;
import static utilz.constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadsAnimation();
    }

    public void update() {
        updatePos();
        updateAnimationonTick();
        setAnimation();

    }

    // In player ra screen
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);
    }

    // Chuyển frame của mỗi animation
    private void updateAnimationonTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    // Animation về hành động
    private void setAnimation() {
        int startAni = playerAction;
        if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if(up){
            playerAction = JUMP;
        }

        // Nếu chuyeren hành động thì chạy animation mới
        if (startAni != playerAction) {
            resetAninTick();
        }
    }

    private void resetAninTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void setRectPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Nhân vật di chuyển
    private void updatePos() {
        moving = false;

        // Left
        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        }
        // Right
        else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }
        // Up
        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        }
        // Down
        else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    // Load ảnh animation
    private void loadsAnimation() {
        animations = new BufferedImage[7][8];

        // IDLE
        for (int i = 0; i < GetSpriteAmount(IDLE); i++) {
            try {
                animations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/player/idle/idle-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BACK-JUMP
        for (int i = 0; i < GetSpriteAmount(BACK_JUMP); i++) {
            try {
                animations[BACK_JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/back-jump/back-jump-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // JUMP
        for (int i = 0; i < GetSpriteAmount(JUMP); i++) {
            try {
                animations[JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/jump/jump-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // HURT
        for (int i = 0; i < GetSpriteAmount(HURT); i++) {
            try {
                animations[HURT][i] = ImageIO.read(getClass().getResourceAsStream("/player/hurt/hurt.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // RUN
        for (int i = 0; i < GetSpriteAmount(RUN); i++) {
            try {
                animations[RUN][i] = ImageIO.read(getClass().getResourceAsStream("/player/run/run-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // RUN-SHOOT
        for (int i = 0; i < GetSpriteAmount(RUN_SHOOT); i++) {
            try {
                animations[RUN_SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/run-shoot/run-shoot-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // SHOOT
        for (int i = 0; i < GetSpriteAmount(SHOOT); i++) {
            try {
                animations[SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/shoot/shoot.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    // Set đứng yên
    public void resetDirBoleans() {
        left = false;
        right = false;
        down = false;
        up = false;
    }
}
