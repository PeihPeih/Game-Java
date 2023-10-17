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
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
    }


    public void loadObjects(Level newLevel) {
        bullets.clear();
    }


    public void update(int[][] lvlData, Player player) {
    }



    public void draw(Graphics g, int xLvlOffset) {
//        drawBullets(g, xLvlOffset);
    }


    public void resetAllObjects() {
//        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Bullet b : bullets)
            b.reset();
    }
}
