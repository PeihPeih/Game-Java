package entities;

import main.Game;
import utilz.LoadSave;

import static utilz.constants.Direction.LEFT;
import static utilz.constants.Direction.RIGHT;
import static utilz.constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class FrostDemon extends Enemy {

    // Init
    public FrostDemon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FROST_DEMON);
        this.walkSpeed = 0.45f*Game.SCALE;
        initHitbox(x, y, 45 * Game.SCALE, 50 * Game.SCALE);
        initAttackBox();
    }
    // Tao attackHitBox cho mob
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y,(int) 50 * Game.SCALE,(int) 50 * Game.SCALE);
        attackBoxOffsetX = (int) (Game.SCALE * 45);
    }

    // Update
    public void update(int[][] lvlData, Player player) {
        updateAnimationTicks();
        updateBehaviour(lvlData, player);
        updateAttackBox();
    }

    private void updateAttackBox() {
        // Update attack hitbox theo huong di cua quai
        if(walkDir == LEFT){
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }
        else if(walkDir == RIGHT) {
            attackBox.x = hitbox.x + attackBoxOffsetX - 10;
        }
        attackBox.y = hitbox.y;
    }



    private void updateBehaviour(int[][] lvlData, Player player) {
        // falling when start
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            // if in air keep falling down
            updateInAir(lvlData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(WALK);
                    break;
                case WALK:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseToAttack(player))
                            newState(CLEAVE);

                    }
                    move(lvlData);
                    break;
                case CLEAVE:
                    if(aniIndex == 0)
                        attackChecked = false;
                    if(aniIndex == 7 && !attackChecked)
                    {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case TAKE_HIT:
                    break;
            }
        }
    }


    // Used to flip the image, right -> left
    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;

    }
}
