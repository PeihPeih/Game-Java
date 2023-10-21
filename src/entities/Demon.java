package entities;

import main.Game;

import static utilz.HelpMethods.*;
import static utilz.constants.Direction.LEFT;
import static utilz.constants.Direction.RIGHT;
import static utilz.constants.EnemyConstants.*;

public class Demon extends Enemy {
    // Init
    public Demon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FIRE_DEMON);
        initHitbox(x, y, 25 * Game.SCALE, 40 * Game.SCALE);
    }

    // Update
    public void update(int[][] lvlData, Player player) {
        updateAnimationTicks();
        updateMove(lvlData, player);
    }

    private void updateMove(int[][] lvlData, Player player) {
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
