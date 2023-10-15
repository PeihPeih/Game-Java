package gamestate;

import UI.PauseOverlay;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Playing extends State implements Statemethods {
    private LevelManager levelManager;
    private Player player;

    private boolean paused=false;
    private PauseOverlay pauseOverlay;
    // Background
    private BufferedImage[] backgroundImage;
    private void loadBackground() {//load background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
    }
    private void drawBackground(Graphics g) {// ve background
        if (backgroundImage[0] != null) g.drawImage(backgroundImage[0], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        if (backgroundImage[1] != null) g.drawImage(backgroundImage[1], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        if (backgroundImage[2] != null)
            g.drawImage(backgroundImage[2], 0, (int) (Game.GAME_HEIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
        if (backgroundImage[4] != null)
            g.drawImage(backgroundImage[4], Game.GAME_WIDTH - (int) (backgroundImage[4].getWidth() * 1.5), (int) (Game.GAME_HEIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
        if (backgroundImage[3] != null)
            g.drawImage(backgroundImage[3], 0, (int) (Game.GAME_HEIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);
    }

    public Playing(Game game) throws IOException {
        super(game);
        innitClasses();

    }

    private void innitClasses() throws IOException {// khoi tao
        loadBackground();
        levelManager = new LevelManager(game);
        player = new Player(100, 200, 82, 77);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {//update
        if(!paused){
            levelManager.update();
            player.update();
        }
        else
            pauseOverlay.update();
    }

    @Override
    public void draw(Graphics g) {// ve map nhan vat va background
        drawBackground(g);
        if (levelManager != null) {
            levelManager.render(g);
        }

        if (player != null) {
            player.render(g);
        }
        if(paused)
            pauseOverlay.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressd(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressd(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e){
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {// dieu khien nhan vat
        switch (e.getKeyCode()){
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
                paused=!paused;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // thả key thì k di chuyển
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
    public void unpausedGame(){
        paused=false;
    }
    public void windowFocusLost() {
        player.resetDirBoleans();
    }

    //getter Player
    public Player getPlayer() {
        return player;
    }
}
