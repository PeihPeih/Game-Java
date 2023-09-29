package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.constants.Direction.*;
import static utils.constants.Direction.DOWN;
import static utils.constants.PlayerConstants.*;

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

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex],(int) x,(int) y,256,160,null);
    }

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
    private void setAnimation(){
        int startAni = playerAction;
        if(moving){
            playerAction = RUNNING;
        }else {
            playerAction = IDLE;
        }

        if(attacking){
            playerAction = ATTACK_1;
        }
        if(startAni != playerAction){
            resetAninTick();
        }
    }
    private void resetAninTick(){
        aniTick = 0;
        aniIndex = 0;
    }

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

    private void loadsAnimation() {

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
