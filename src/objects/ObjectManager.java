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

import static utilz.constants.ObjectConstants.*;
import static utilz.HelpMethods.IsBulletsHittingLevel;
import static utilz.constants.Bullet.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage heart;
    private ArrayList<Heart> hearts;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        loadObjects();
    }

    private void loadImgs() {
        heart = LoadSave.GetSpriteAtlas(LoadSave.HEART);
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


    public void loadObjects() {
        hearts = new ArrayList<>(LoadSave.GetHearts(1));
        System.out.println(hearts.size());
    }


    public void update(int[][] lvlData, Player player) {
    }


    public void draw(Graphics g, int xLvlOffset) {
        drawHearts(g, xLvlOffset);
    }

    private void drawHearts(Graphics g, int xLvlOffset) {
        for (Heart h : hearts)
            if (h.isActive()) {
                g.drawImage(heart, (int) (h.getHitbox().x - h.getxDrawOffset() - xLvlOffset), (int) (h.getHitbox().y - h.getyDrawOffset()), HEART_WIDTH, HEART_HEIGHT,null);
            }
    }


    public void resetAllObjects() {
        for (Heart h : hearts)
            h.reset();
    }
}
