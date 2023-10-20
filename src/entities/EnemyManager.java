package entities;

import gamestate.Playing;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] animations;
    private ArrayList<Demon> demons = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    public void update(int[][] lvlData, Player player) {
        for (Demon d : demons) {
            d.update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawDemons(g, xLvlOffset);
    }

    private void drawDemons(Graphics g, int xLvlOffset) {
        for (Demon d : demons) {
            g.drawImage(animations[d.getEnemyState()][d.getAniIndex()], (int) (d.getHitbox().x) - xLvlOffset - DEMON_DRAWOFFSET_X  + d.flipX(), (int) d.getHitbox().y-DEMON_DRAWOFFSET_Y, DEMON_WIDTH*d.flipW(), DEMON_HEIGHT, null);
            d.drawHitbox(g,xLvlOffset);
        }
    }


    private void addEnemies() {
        demons = LoadSave.GetDemons(1);
    }

    private void loadEnemyImgs() {
        animations = new BufferedImage[7][22];
        // IDLE
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, IDLE); i++) {
            try {
                animations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/01_demon_idle/demon_idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // WALK
        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, WALK); i++) {
            try {
                animations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/02_demon_walk/demon_walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, CLEAVE); i++) {
            try {
                animations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/03_demon_cleave/demon_cleave_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, TAKE_HIT); i++) {
            try {
                animations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/04_demon_take_hit/demon_take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpriteAmount(FIRE_DEMON, DEATH); i++) {
            try {
                animations[DEATH][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/05_demon_death/demon_death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
