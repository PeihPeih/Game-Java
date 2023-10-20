package entities;

import main.Game;

import static utilz.HelpMethods.*;
import static utilz.constants.EnemyConstants.*;
import static utilz.constants.Direction.*;

public abstract class Enemy extends Entity {
   protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 12;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 1.0f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE; // kcanh tan cong = kcah 1 o

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState = IDLE;
        initHitbox(x, y, width, height);
    }

    protected void updateAnimationTicks() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                if(enemyState == CLEAVE) enemyState = IDLE; // change enemy state affter attack
            }
        }
    }
    protected void firstUpdateCheck(int[][] lvlData){
        if (!IsEntityOntheFloor(hitbox, lvlData)) inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData){
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPosUnderRootOrAboveFloor(hitbox, aniSpeed);
            tileY = (int)(hitbox.y/Game.TILES_SIZE); // ô của enemy
        }
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT){
            xSpeed = -walkSpeed;
        }else {
            xSpeed = walkSpeed;
        }

        // can move left or right
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            if (IsFloor(hitbox,xSpeed,lvlData)){
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player){
        if(player.hitbox.x > hitbox.x){
            walkDir = RIGHT;
        }else {
            walkDir = LEFT;
        }
    }
    protected boolean canSeePlayer(int[][] lvlData,Player player ){
        int playerTileY = (int)(player.getHitbox().y/Game.TILES_SIZE);
        if(playerTileY == tileY)
            // enemy player cùng 1 ô Y check xem có trong phạm vi attack k
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }

        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        // attack range
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 10;
    }
    protected boolean isPlayerCloseToAttack(Player player){
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void newState(int enemyState){
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) walkDir = RIGHT;
        else walkDir = LEFT;
    }

    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
