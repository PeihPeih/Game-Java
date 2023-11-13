package gamestate;

import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverlay;
import audio.AudioPlayer;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.*;
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
    private boolean checkBorder = true;
    private boolean existBoss = false;

    private boolean gameover;
    private boolean lvlcompleted = false ;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private Font font;
    double Playingtime;
    double Gaptime=0;
    long currentTime;
    boolean timeacivate=false;



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
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        font = new Font("GravityRegular5", Font.PLAIN,16);
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
    public void loadNextlevel() throws IOException {
        resetALL();
        levelManager.loadNextLevel();
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

    public void checkPlayerTrap(Trap t) {
        player.checkPlayerTrap(t);
    }

    private double CountTimes(){
        return (double) ((System.currentTimeMillis()-currentTime)/10);
    }

    @Override
    public void update() {//update
       gameover = player.IsDeath();
        if (!paused && !gameover && !lvlcompleted) {
        	
            levelManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            if(player.IsDeath())
            {
            	getGame().getAudioPlayer().stopSong();
            	getGame().getAudioPlayer().playEffect(AudioPlayer.GAME_OVER);
            }
            

            if (levelManager.getLvlIndex() == levelManager.getAmountOfLevels() - 1) {
                if(!existBoss){
                    existBoss = true;
                    enemyManager.getFinalBoss().setExist(true);
                }

                if (player.getHitbox().x >= levelManager.getCurrentLevel().getWidthLevel() * 97 / 100) {
                    enemyManager.getFinalBoss().setCanMove(true);
                    objectManager.setSpawn(false);
                    checkBorder = false;
                }
            }
            if (checkBorder) {
                checkCloseToBorder();
            }
        }

        if (gameover)
        {
        	gameOverOverlay.update();
        }
            
        else if(paused)
            pauseOverlay.update();
        else if (lvlcompleted)
            levelCompletedOverlay.update();
    }

    @Override
    public void draw(Graphics g) {// ve map nhan vat va background
        //gameover = player.IsDeath();

        if(!timeacivate){
            currentTime = System.currentTimeMillis();
            timeacivate=true;
        }

        drawBackground(g, xLvlOffset);

        drawCloud(g, xLvlOffset);

        if (levelManager != null) levelManager.render(g, xLvlOffset);
        if(enemyManager!=null){
            enemyManager.getFinalBoss().drawWarnings(g,xLvlOffset);
            enemyManager.getFinalBoss().drawTraps(g,xLvlOffset);
        }
        if (player != null) player.render(g, xLvlOffset);
        if (enemyManager != null) enemyManager.draw(g, xLvlOffset);
        if (objectManager != null) objectManager.draw(g, xLvlOffset);
        if(enemyManager!=null){
            enemyManager.getFinalBoss().drawHealthBar(g);
        }

        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Times:",1144,53);
        TimeCounter();
        g.drawString(String.valueOf(Playingtime),1244,53);


        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameover) {
            gameOverOverlay.draw(g);
        }
        else if(lvlcompleted){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            levelCompletedOverlay.draw(g);
        }
    }
    public void TimeCounter(){
        if(!paused && !lvlcompleted && !gameover){
            Playingtime =CountTimes()/100-Gaptime;
            Playingtime =((double) Math.floor(Playingtime*100)/100);
        }
        else {
            Gaptime=CountTimes()/100-Playingtime;
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
        lvlcompleted=false;
        checkBorder = true;
        existBoss = false;

        levelCompletedOverlay.setAniIndex(0);
        player.resetALl();
        enemyManager.resetEnemies();
        objectManager.resetAllObjects();
    }
    public void  resetLvl(){
        resetALL();
        lvlcompleted=false;
        levelManager.loadLevel();
    }
    public void resetTime(){
        timeacivate=false;
        Playingtime=0;
        Gaptime=0;
        currentTime = System.currentTimeMillis();
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
        else if(lvlcompleted)
            levelCompletedOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) throws IOException {
        if (paused)
            pauseOverlay.mouseReleased(e);
        else if (gameover)
            gameOverOverlay.mouseReleased(e);
        else if(lvlcompleted)
            levelCompletedOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
        else if (gameover)
            gameOverOverlay.mouseMoved(e);
        else if(lvlcompleted)
            levelCompletedOverlay.mouseMoved(e);
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
                case KeyEvent.VK_W, KeyEvent.VK_SPACE:
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
                case KeyEvent.VK_W, KeyEvent.VK_SPACE:
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
    public void setLvlcompleted(boolean lvlcompleted) {
        this.lvlcompleted = lvlcompleted;
        if(lvlcompleted)
        {
        	game.getAudioPlayer().lvlCompleted();
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

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public double getPlayingtime() {
        return Playingtime;
    }
}
