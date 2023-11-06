package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.FireDemon;
import entities.FrostDemon;
import entities.ShadowDemon;
import main.Game;
import objects.Heart;
import utilz.HelpMethods;

import static utilz.HelpMethods.*;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<FireDemon> fireDemons;
    private ArrayList<FrostDemon> frostDemons;
    private ArrayList<ShadowDemon> shadowDemons;
    private ArrayList<Heart> hearts;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createHearts();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void createHearts() {
        hearts = HelpMethods.GetHearts(img);
    }

    private void calcPlayerSpawn() {
//        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        fireDemons = GetFireDemons(this.img);
        frostDemons=GetFrostDemons(this.img);
        shadowDemons=GetShadowDemon(this.img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public BufferedImage getImage(){
        return this.img;
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public int getWidthLevel(){
        return img.getWidth()*Game.TILES_SIZE;
    }

    public ArrayList<FireDemon> getFireDemons() {
        return fireDemons;
    }

    public ArrayList<FrostDemon> getFrostDemons() {
        return frostDemons;
    }

    public ArrayList<ShadowDemon> getShadowDemons() {
        return shadowDemons;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

}
