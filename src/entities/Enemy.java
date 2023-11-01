package entities;

import main.Game;

import static utilz.HelpMethods.*;
import static utilz.constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import static utilz.constants.Direction.*;

public abstract class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 12;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE; // kcach tan cong = 1 ô
    protected boolean active = true;
    protected boolean isDead = false;
    protected boolean attackChecked;
    protected Rectangle2D.Float attackBox;
    protected int attackBoxOffsetX;
    protected int maxHeath;
    protected int currentHealth;

    // Init
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState = IDLE;
        maxHeath = GetMaxHeath(enemyType);
        currentHealth = maxHeath;
        initHitbox(x, y, width, height);
    }


    // Animation
    protected void updateAnimationTicks() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;

                switch (enemyState) {
                    case CLEAVE, TAKE_HIT -> enemyState = IDLE; // change enemy state after attack or take hit
                    case DEAD -> active = false;    // enemy is no longer active -> skip all updates
                }

            }
        }
    }

    // Ve hitbox cua attack
    protected void drawAttackHitbox(Graphics g, int xLvlOffset)
    {
        g.setColor(Color.PINK);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }


    // Enemy fall if it's in air
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOntheFloor(hitbox, lvlData)) inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPosUnderRootOrAboveFloor(hitbox, aniSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE); // ô của enemy
        }
    }

    // Move
    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        // can move left or right
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        // if can't move, change direction
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            // enemy player cùng 1 ô Y check xem có trong phạm vi attack k
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }

        return false;
    }

    // check player có trong khoảng để auto di chuyển  đến không
    protected boolean isPlayerInRange(Player player) {
        // attack range
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    // Check player đủ gần để attack không
    protected boolean isPlayerCloseToAttack(Player player) {
        if (hitbox.x < player.getHitbox().x) {
            return attackBox.x + attackBox.width >= player.getHitbox().x+15;
        }
        return attackBox.x <= player.getHitbox().x + player.getHitbox().width-15;
    }


    // Check player co bi tan cong khong
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.getHitbox())) {
            player.minusHeart(1);
            attackChecked = true;
        }
    }

    //Enemy bị tấn công
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0){
            newState(DEAD);
            isDead=true;
        }
        else
            newState(TAKE_HIT);
    }

    // Set the new state
    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) walkDir = RIGHT;
        else walkDir = LEFT;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHeath;
        newState(IDLE);
        active = true;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDead(){
        return isDead;
    }
}
