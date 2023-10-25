package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestate.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import utilz.constants;

import javax.imageio.ImageIO;

import static utilz.HelpMethods.IsBombsHittingLevel;
import static utilz.constants.ObjectConstants.*;
import static utilz.HelpMethods.IsBulletsHittingLevel;
import static utilz.constants.Bullet.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage heart;
    private BufferedImage bomb;
    private ArrayList<Heart> hearts;
    private ArrayList<Bomb> bombs;

    // Spawn bomb
    private int startSpawnTimer = 5 * 180;
    private int timeStart = 0;
    private int spawnBombTimer;
    private int spawnBombTimerMax = 150;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        this.spawnBombTimer = this.spawnBombTimerMax;
    }

    private void loadImgs() {
        heart = LoadSave.GetSpriteAtlas(LoadSave.HEART);
        bomb = LoadSave.GetSpriteAtlas(LoadSave.BOMB);
    }


    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Heart h : hearts)
            if (h.isActive()) {
                if (hitbox.intersects(h.getHitbox())) {
                    h.setActive(false);
                    playing.getPlayer().addHeart();
                }
            }
    }


    public void loadObjects(Level newLevel) {
        hearts = new ArrayList<>(newLevel.getHearts());
        bombs = new ArrayList<>();
    }


    public void update(int[][] lvlData, Player player) {
        timeStart++;
        if (timeStart >= startSpawnTimer) {
            spawnBomb(player);
            updateBombs(lvlData, player);
        }
    }

    private void spawnBomb(Player player) {
        if (spawnBombTimer >= spawnBombTimerMax) {
            bombs.add(new Bomb((int) (player.getHitbox().x - player.getHitbox().width / 2), -100, BOMB));
            spawnBombTimer = 0;
        }
        spawnBombTimer++;
    }

    private void updateBombs(int[][] lvlData, Player player) {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isDestroy()) {
                bombs.get(i).update();
                if (bombs.get(i).getHitbox().intersects(player.getHitbox())) {
                    player.minusHeart();
                    bombs.get(i).setDestroy(true);
                } else if (IsBombsHittingLevel(bombs.get(i), lvlData)) {
                    bombs.get(i).setDestroy(true);
                }
            }
        }
    }


    public void draw(Graphics g, int xLvlOffset) {
        drawHearts(g, xLvlOffset);
        drawBombs(g, xLvlOffset);
    }

    private void drawHearts(Graphics g, int xLvlOffset) {
        for (Heart h : hearts)
            if (h.isActive()) {
                g.drawImage(heart, (int) (h.getHitbox().x - h.getxDrawOffset() - xLvlOffset), (int) (h.getHitbox().y - h.getyDrawOffset()), HEART_WIDTH, HEART_HEIGHT, null);
            }
    }

    private void drawBombs(Graphics g, int xLvlOffset) {
        for (int i = 0; i < bombs.size(); i++)
            bombs.get(i).draw(g, xLvlOffset);
    }

    public void resetAllObjects() {
        timeStart = 0;
        for (Heart h : hearts)
            h.reset();
        while (bombs.size() > 0) {
            bombs.remove(0);
        }
    }

    public void destroy() {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isActive()) {
                bombs.remove(i);
            }
        }
    }
}
