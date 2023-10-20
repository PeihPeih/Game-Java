package entities;
import main.Game;

import static utilz.HelpMethods.*;
import static utilz.constants.Direction.LEFT;
import static utilz.constants.EnemyConstants.*;
public class Demon extends Enemy {
    public Demon(float x,float y){
        super(x,y,DEMON_WIDTH,DEMON_HEIGHT,DEMON);
        initHitbox(x,y,(int)(22* Game.SCALE),(int)(19*Game.SCALE));
    }
    public void update(int[][] lvlData,Player player) {
        updateMove(lvlData,player);
        updateAnimationTicks();

    }
    private void updateMove(int[][] lvlData,Player player) {
        // falling when start
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            // iff in air keep falling down
            updateInAir(lvlData);
        }else {
            switch(enemyState){
                case IDLE:
                   newState(WALK);
                case WALK:
                    if (canSeePlayer(lvlData,player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseToAttack(player))
                        newState(CLEAVE);

                    move(lvlData);
                    break;
            }
        }
    }
}
