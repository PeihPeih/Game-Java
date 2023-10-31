package entities;

import gamestate.Playing;
import levels.Level;
import main.Game;
import objects.Bullet;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.EnemyConstants.*;

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
        this.finalBoss = new FinalBoss(0, Game.GAME_HEIGHT/2,200,200);
        loadEnemyImgs();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (FireDemon d : fireDemons) {
            d.update(lvlData, player);
            isAnyActive = true;
        }
        for (FrostDemon d : frostDemons) {
            d.update(lvlData, player);
            isAnyActive = true;
        }
        for (ShadowDemon d : shadowDemons) {
            d.update(lvlData, player);
            isAnyActive = true;
        }
        finalBoss.update();
//        if (!isAnyActive)
//            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawDemons(g, xLvlOffset);
    }

    private void drawDemons(Graphics g, int xLvlOffset) {
        // Fire demons
        for (FireDemon d : fireDemons) {
            // Neu mob con song thi update
            if (d.isActive()) {
                g.drawImage(fireAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - FIRE_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - FIRE_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
                // Ve hitbox cho mob
                d.drawHitbox(g, xLvlOffset);
                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Frost demons
        for (FrostDemon d : frostDemons) {
            // Neu mob con song thi update
            if (d.isActive()) {
                g.drawImage(frostAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - FROST_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - FROST_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
                // Ve hitbox cho mob
                d.drawHitbox(g, xLvlOffset);
                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Shadow demons
        for (ShadowDemon d : shadowDemons) {
            // Neu mob con song thi update
            if (d.isActive()) {
                g.drawImage(shadowAnimations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - SHADOW_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitbox().y - SHADOW_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
                // Ve hitbox cho mob
                d.drawHitbox(g, xLvlOffset);
                d.drawAttackHitbox(g, xLvlOffset);
            }
        }

        // Final boss
        finalBoss.draw(g,xLvlOffset);
    }

    // Load enemies's animation
    private void loadEnemyImgs() {
        // Fire demons
        fireAnimations = new BufferedImage[7][22];
        // IDLE
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, IDLE); i++) {
            try {
                fireAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/01_demon_idle/demon_idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // WALK
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, WALK); i++) {
            try {
                fireAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/02_demon_walk/demon_walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // CLEAVE
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, CLEAVE); i++) {
            try {
                fireAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/03_demon_cleave/demon_cleave_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TAKE_HIT
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, TAKE_HIT); i++) {
            try {
                fireAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/04_demon_take_hit/demon_take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // DEATH
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, DEAD); i++) {
            try {
                fireAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/05_demon_death/demon_death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Frost demons
        frostAnimations = new BufferedImage[7][16];
        // IDLE
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, IDLE); i++) {
            try {
                frostAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/idle/idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // WALK
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, WALK); i++) {
            try {
                frostAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/walk/walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // CLEAVE
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, CLEAVE); i++) {
            try {
                frostAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/1_atk/1_atk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TAKE_HIT
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, TAKE_HIT); i++) {
            try {
                frostAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/take_hit/take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // DEATH
        for (int i = 0; i < GetSpriteAmount(FROST_DEMON, DEAD); i++) {
            try {
                frostAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/death/death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Shadow demons
        shadowAnimations = new BufferedImage[7][10];
        // IDLE
        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, IDLE); i++) {
            try {
                shadowAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Idle/Bringer-of-Death_Idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // WALK
        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, WALK); i++) {
            try {
                shadowAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Walk/Bringer-of-Death_Walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // CLEAVE
        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, CLEAVE); i++) {
            try {
                shadowAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Attack/Bringer-of-Death_Attack_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TAKE_HIT
        for (int i = 0; i < GetSpriteAmount(SHADOW_DEMON, TAKE_HIT); i++) {
            try {
                shadowAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Hurt/Bringer-of-Death_Hurt_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // DEATH
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
        frostDemons=level.getFrostDemons();
        shadowDemons=level.getShadowDemons();
    }

    // Check xem bị bắn hay không
    public void checkEnemyHit(Bullet b) {
        Rectangle attackBox = b.getHitbox().getBounds();
        for (int i = 0; i < fireDemons.size(); i++) {
            if (fireDemons.get(i).isActive() && fireDemons.get(i).getEnemyState() != DEAD)
                if (fireDemons.get(i).getHitbox().intersects(attackBox)) {
                    fireDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }

        for (int i = 0; i < frostDemons.size(); i++) {
            if (frostDemons.get(i).isActive() && frostDemons.get(i).getEnemyState() != DEAD)
                if (frostDemons.get(i).getHitbox().intersects(attackBox)) {
                    frostDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }

        for (int i = 0; i < shadowDemons.size(); i++) {
            if (shadowDemons.get(i).isActive() && shadowDemons.get(i).getEnemyState() != DEAD)
                if (shadowDemons.get(i).getHitbox().intersects(attackBox)) {
                    shadowDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
        }
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
    }
}
