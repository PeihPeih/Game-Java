package gamestate;

import UI.GameOverOverlay;
import UI.PauseOverlay;

import entities.EnemyManager;
import entities.FinalBoss;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.Bullet;
import objects.Laser;
import objects.ObjectManager;
import objects.ProjectileBoss;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


public class Playing extends State implements Statemethods {

    private EnemyManager enemyManager;
    private LevelManager levelManager;
    private Player player;
    private ObjectManager objectManager;

    private boolean paused = false;

    private boolean gameover;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;

    // Camera
    private int xLvlOffset; //
    private int leftBorder = (int) (0.2 * GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int maxLvlOffsetX;

    // Background
    private BufferedImage[] backgroundImage;
    private BufferedImage cloud;

    public Playing(Game game) throws IOException {
        super(game);
        innitClasses();
        calcLvlOffset();
        loadStartLevel();
    }

    private void innitClasses() throws IOException {// khoi tao
        loadBackground();

        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(100, 200, 82, 77, this);
        objectManager = new ObjectManager(this);

        player = new Player(100, 200, 82, 77, this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    // BACKGROUND
    private void loadBackground() {//load background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
        cloud = LoadSave.GetSpriteAtlas(LoadSave.CLOUD);
    }


    private void drawBackground(Graphics g, int xLvlOffset) {
        int bgImageWidth = GAME_WIDTH;
        int bgOffset = xLvlOffset % bgImageWidth;
        int amountBgToFillScreen = (int) Math.ceil(1.0 * GAME_WIDTH / bgImageWidth) + 1;

        // VẼ
        for (int i = 0; i < amountBgToFillScreen; i++) {
            // LAYER 0
            if (backgroundImage[0] != null)
                g.drawImage(backgroundImage[0], i * bgImageWidth - bgOffset, 0, bgImageWidth, GAME_HEIGHT, null);
            // LAYER 1
            if (backgroundImage[1] != null)
                g.drawImage(backgroundImage[1], i * bgImageWidth - bgOffset, 0, bgImageWidth, GAME_HEIGHT, null);
            // LAYER 2
            if (backgroundImage[2] != null)
                g.drawImage(backgroundImage[2], i * bgImageWidth - bgOffset, (int) (GAME_HEIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
            // LAYER 4
            if (backgroundImage[4] != null)
                g.drawImage(backgroundImage[4], i * bgImageWidth + GAME_WIDTH - (int) (backgroundImage[4].getWidth() * 1.5) - bgOffset, (int) (GAME_HEIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
            // LAYER 3
            if (backgroundImage[3] != null)
                g.drawImage(backgroundImage[3], i * bgImageWidth - bgOffset, (int) (GAME_HEIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);
        }

    }

    private void drawCloud(Graphics g, int xLvlOffset) {
        int bgImageWidth = GAME_WIDTH;
        int bgOffset = xLvlOffset % bgImageWidth;
        int amountBgToFillScreen = (int) Math.ceil(1.0 * GAME_WIDTH / bgImageWidth) + 1;

        // VẼ
        for (int i = 0; i < amountBgToFillScreen; i++) {
            // LAYER 0
            if (cloud != null)
                g.drawImage(cloud, i * bgImageWidth - bgOffset, -200, bgImageWidth, 400, null);

        }
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    public void checkHeartTouch(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkEnemyHit(Bullet b) {
        enemyManager.checkEnemyHit(b);
    }

    public void checkPlayerHit(ProjectileBoss p) {
        player.checkPlayerHit(p);
    }

    public void checkPlayerLaser(Laser ls) {
        player.checkLaserHit(ls);
    }

    @Override
    public void update() {//update
        gameover = player.IsDeath();
        if (!paused && !gameover) {
            levelManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            if (levelManager.getLvlIndex() == levelManager.getAmountOfLevels() - 1) {
                if (player.getHitbox().x == levelManager.getCurrentLevel().getWidthLevel() - GAME_WIDTH) {
                    enemyManager.getFinalBoss().setActive(true);
                    objectManager.setSpawn(false);
                }
            }
            checkCloseToBorder();
        }
        if (gameover)
            gameOverOverlay.update();
        else
            pauseOverlay.update();

    }

    @Override
    public void draw(Graphics g) {// ve map nhan vat va background
        gameover = player.IsDeath();
        drawBackground(g, xLvlOffset);

        drawCloud(g, xLvlOffset);

        if (levelManager != null) levelManager.render(g, xLvlOffset);
        if (player != null) player.render(g, xLvlOffset);
        if (enemyManager != null) enemyManager.draw(g, xLvlOffset);
        if (objectManager != null) objectManager.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameover) {
            gameOverOverlay.draw(g);
        }
    }

    public void destroy() {
        player.destroy();
        objectManager.destroy();
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    public void resetALL() {
        paused = false;
        gameover = false;
        player.resetALl();
        enemyManager.resetEnemies();
        objectManager.resetAllObjects();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressd(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressd(e);
        else if (gameover)
            gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
        else if (gameover)
            gameOverOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
        else if (gameover)
            gameOverOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {// dieu khien nhan vat
        if (!gameover) {
            switch (e.getKeyCode()) {
                // Move
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                //jump
                case KeyEvent.VK_W:
                    player.setJump(true);
                    break;
                // Attack
                case KeyEvent.VK_J:
                    player.setAttack(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // thả key thì k di chuyển
        if (!gameover) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setJump(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_J:
                    player.setAttack(false);
                    break;

            }
        }

    }

    public void unpausedGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBoleans();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    //getter Player
    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public int getMaxLvlOffset() {
        return maxLvlOffsetX;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}
