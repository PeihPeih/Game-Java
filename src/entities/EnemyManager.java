package entities;

import gamestate.Playing;
import levels.Level;
import main.Game;
import objects.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.EnemyConstants.*;
import static utilz.constants.FinalBossConstants.FINAL_BOSS_HEIGHT;
import static utilz.constants.FinalBossConstants.FINAL_BOSS_WIDTH;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] fireAnimations, frostAnimations, shadowAnimations;
    private ArrayList<FireDemon> fireDemons = new ArrayList<>();
    private ArrayList<FrostDemon> frostDemons = new ArrayList<>();
    private ArrayList<ShadowDemon> shadowDemons = new ArrayList<>();
     private FinalBoss finalBoss;

    // Init
    public EnemyManager(Playing playing) {
        this.playing = playing;


        int finalBossX = playing.getLevelManager().getCurrentLevel().getWidthLevel()+50 ;
        int finalBossY = Game.GAME_HEIGHT/2-FINAL_BOSS_HEIGHT/2;
        this.finalBoss = new FinalBoss(finalBossX, finalBossY, FINAL_BOSS_WIDTH,FINAL_BOSS_HEIGHT, playing);

        loadEnemyImgs();

    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (FireDemon d : fireDemons) {
            d.update(lvlData, player);
            if(d.isActive()) isAnyActive = true;
        }
        for (FrostDemon d : frostDemons) {
            d.update(lvlData, player);
            if(d.isActive())  isAnyActive = true;
        }
        for (ShadowDemon d : shadowDemons) {
            d.update(lvlData, player);
            if(d.isActive())  isAnyActive = true;
        }
         if(finalBoss.isExist()){
            finalBoss.update(lvlData);
            isAnyActive = true;
        }
        playing.setLvlcompleted(!isAnyActive);

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawDemons(g, xLvlOffset);
    }

    private void drawDemons(Graphics g, int xLvlOffset) {
        // Fire demons
        for (FireDemon d : fireDemons) {
            if (d.isActive()) {
                g.drawImage(fireAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - FIRE_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - FIRE_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);

//                d.drawHitbox(g, xLvlOffset);
//                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Frost demons
        for (FrostDemon d : frostDemons) {
            if (d.isActive()) {
                g.drawImage(frostAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - FROST_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - FROST_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);

//                d.drawHitbox(g, xLvlOffset);
//                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Shadow demons
        for (ShadowDemon d : shadowDemons) {
            if (d.isActive()) {
                g.drawImage(shadowAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - SHADOW_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - SHADOW_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);

//                d.drawHitbox(g, xLvlOffset);
//                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Final boss
         finalBoss.draw(g,xLvlOffset);
    }

    // Load enemies's animation
    private void loadEnemyImgs() {
        // Fire demons
        fireAnimations = new BufferedImage[7][22];
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, IDLE); i++) {
            try {
                fireAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/01_demon_idle/demon_idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, WALK); i++) {
            try {
                fireAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/02_demon_walk/demon_walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, CLEAVE); i++) {
            try {
                fireAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/03_demon_cleave/demon_cleave_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, TAKE_HIT); i++) {
            try {
                fireAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/04_demon_take_hit/demon_take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, DEAD); i++) {
            try {
                fireAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/05_demon_death/demon_death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Frost demons
        frostAnimations = new BufferedImage[7][16];
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, IDLE); i++) {
            try {
                frostAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/idle/idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, WALK); i++) {
            try {
                frostAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/walk/walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, CLEAVE); i++) {
            try {
                frostAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/1_atk/1_atk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, TAKE_HIT); i++) {
            try {
                frostAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/take_hit/take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, DEAD); i++) {
            try {
                frostAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/death/death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Shadow demons
        shadowAnimations = new BufferedImage[7][10];
        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, IDLE); i++) {
            try {
                shadowAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Idle/Bringer-of-Death_Idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, WALK); i++) {
            try {
                shadowAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Walk/Bringer-of-Death_Walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, CLEAVE); i++) {
            try {
                shadowAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Attack/Bringer-of-Death_Attack_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, TAKE_HIT); i++) {
            try {
                shadowAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Hurt/Bringer-of-Death_Hurt_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, DEAD); i++) {
            try {
                shadowAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Death/Bringer-of-Death_Death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadEnemies(Level level) {
        fireDemons = level.getFireDemons();
        frostDemons = level.getFrostDemons();
        shadowDemons = level.getShadowDemons();
    }

    // Check xem bị bắn hay không
    public void checkEnemyHit(Bullet b) {
        Rectangle attackBox = b.getHitbox().getBounds();
        for (int i = 0; i < fireDemons.size(); i++) {
            if (!fireDemons.get(i).isDead() && fireDemons.get(i).getEnemyState() != DEAD)
                if (fireDemons.get(i).getHitbox().intersects(attackBox)) {
                    fireDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }

        for (int i = 0; i < frostDemons.size(); i++) {
            if (!frostDemons.get(i).isDead() && frostDemons.get(i).getEnemyState() != DEAD)
                if (frostDemons.get(i).getHitbox().intersects(attackBox)) {
                    frostDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }

        for (int i = 0; i < shadowDemons.size(); i++) {
            if (!shadowDemons.get(i).isDead() && shadowDemons.get(i).getEnemyState() != DEAD)
                if (shadowDemons.get(i).getHitbox().intersects(attackBox)) {
                    shadowDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }

     if(finalBoss.getState() != DEAD && !finalBoss.isDead()){
            if(finalBoss.getHitbox().intersects(attackBox)){
                if(finalBoss.isHurt()){
                    finalBoss.hurt(playing.getPlayer().getDamage() - (int)finalBoss.getArmor());
                    b.setActive(false);
                }
            }
        }
    }

    public FinalBoss getFinalBoss(){
      return finalBoss;
    }

    public void resetEnemies() {
        for (FireDemon d : fireDemons) {
            d.resetEnemy();
        }
        for (FrostDemon d : frostDemons) {
            d.resetEnemy();
        }
        for (ShadowDemon d : shadowDemons) {
            d.resetEnemy();
        }
        finalBoss.resetAll();
    }
}
