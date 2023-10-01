package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.constants.Direction.*;
import static utilz.constants.PlayerConstants.*;

public class Player extends  Entity{
    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false,attacking = false;
    private boolean left,up,right,down;
    private float playerSpeed = 2.0f;
    public Player(float x,float y){
        super(x,y);
        loadsAnimation();
    }
    public void update() {
        updatePos();
        updateAnimationonTick();
        setAnimation();

    }

    // In player ra screen
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex],(int) x,(int) y,256,160,null);
    }

    // Chuyển frame của mỗi animation
    private void updateAnimationonTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    // Animation về hành động
    private void setAnimation(){
        int startAni = playerAction;
//        if(moving){
//            playerAction = RUNNING;
//        }else {
//            playerAction = IDLE;
//        }
//
//        if(attacking){
//            playerAction = ATTACK_1;
//        }

        // Nếu chuyeren hành động thì chạy animation mới
        if(startAni != playerAction){
            resetAninTick();
        }
    }

    private void resetAninTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    // Nhân vật di chuyển
    private void updatePos(){
        moving = false;

        if(left && !right){
            x-=playerSpeed;
            moving = true;
        }else if(right && !left){
            x+=playerSpeed;
            moving = true;
        }

        if(up && !down){
            y-=playerSpeed;
            moving = true;
        }else if(down && !up){
            y+=playerSpeed;
            moving = true;
        }
    }

    // Load ảnh animation
    private void loadsAnimation() {
        animations = new BufferedImage[6][8]; // 6 là số folder ảnh t back-jump -> run-shoot
        // 8 là số ảnh hành động lớn nhất tính trong 6 folder đó

        // Ilde
        for(int  i = 0 ;i<GetSpriteAmount(IDLE);i++){
                try {
                    // animation[playerAction][aniIndex]
                    animations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/player/idle/idle-"+(i+1)+".png"));
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

        // Back jump
        for(int  i = 0 ;i<GetSpriteAmount(BACK_JUMP);i++){
            try {
                animations[BACK_JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/back-jump/back-jump-"+(i+1)+".png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // jump
        for(int  i = 0 ;i<GetSpriteAmount(JUMP);i++){
            try {
                animations[JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/jump/jump-"+(i+1)+".png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // hurt
        for(int  i = 0 ;i<GetSpriteAmount(HURT);i++){
            try {
                animations[HURT][i] = ImageIO.read(getClass().getResourceAsStream("/player/hurt/hurt-"+(i+1)+".png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // Run
        for(int  i = 0 ;i<GetSpriteAmount(RUN);i++){
            try {
                animations[RUN][i] = ImageIO.read(getClass().getResourceAsStream("/player/run/run-"+(i+1)+".png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // Ilde
        for(int  i = 0 ;i<GetSpriteAmount(RUN_SHOOT);i++){
            try {
                animations[RUN_SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/run-shoot/run-shoot-"+(i+1)+".png"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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

    public void resetDirBoleans(){
        left = false;
        right = false;
        down = false;
        up = false;
    }
    public void setAttack(boolean attacking){
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
}
