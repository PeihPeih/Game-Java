package main;

import levels.LevelManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import entities.Player;
import utilz.LoadSave;


public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private LevelManager levelManager;
    private Player player;

    // Background
    private BufferedImage[] backgroundImage;
    private int[] smallCloudsPos;
    private Random random = new Random();


    // SYSTEM
    private final int FPS_CAP = 120;    // FPS (frame per second) gioi han 1 giay lam moi bao nhieu frame
    private final int UPS_CAP = 180;    // UPS (update per second) gioi han so lan lam moi 1 logic/s

    // WIDTH AND HEIGHT OF WINDOW
    // Tiles
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_WIDTH = 28; // 28 cột
    public final static int TILES_HEIGHT = 15; // 15 hàng
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    // Window
    public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;

    // Khởi tạo
    public Game() {    
        loadBackground();

        innitClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        levelManager = new LevelManager(this);


        gamePanel.setFocusable(true);
        gamePanel.requestFocus();        // yeu cau cac input di vao game panel
        startGameLoop();
    }

    private void loadBackground() {
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
    }

    // khởi tạo 1 đối tượng nh player, enemy,..
    private void innitClasses() {
        player = new Player(100, 200, 91, 86);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start(); // Gọi hàm run ở dưới
    }

    // Vòng lặp vô hạn của game
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_CAP;    // dem xem 1 frame ton tai trong bao nhieu ns
        double timePerUpdate = 1000000000.0 / UPS_CAP;    // thoi gian giua tung logic (chay, di chuyen,...)
        long previousTime = System.nanoTime();            // thoi gian truoc khi chay 1 frame

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();        // thoi gian chay hien tai cua frame
            deltaU += (currentTime - previousTime) / timePerUpdate;     // luu chenh lech giua curTime update va preTime update
            deltaF += (currentTime - previousTime) / timePerFrame;   // luu chenh lech giua curTime frame va preTime frame
            previousTime = currentTime;

            if (deltaU >= 1)    // khi deltaU dat 1 hoac lon hon thi ta update 1 lan
            {
                update();        // update logic
                deltaU--;
                updates++;
            }

            if (deltaF >= 1)    // khi deltaF dat 1 hoac lon hon thi ta ve lai frame
            {
                gamePanel.repaint();    // ve lai frame
                frames++;
                deltaF--;
            }

        }
    }

    // Update
    public void update() {        // update cac hanh dong cua thuc the
        levelManager.update();
        player.update();
    }

    // In ra screen
    public void render(Graphics g) {
        drawBackground(g);

        if (levelManager != null) {
            levelManager.render(g);
        }

        if (player != null) {
            player.render(g);
        }

    }

    private void drawBackground(Graphics g) {
        if (backgroundImage[0] != null) g.drawImage(backgroundImage[0], 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        if (backgroundImage[1] != null) g.drawImage(backgroundImage[1], 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        if (backgroundImage[2] != null)
            g.drawImage(backgroundImage[2], 0, (int) (GAME_HEIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
        if (backgroundImage[4] != null)
            g.drawImage(backgroundImage[4], GAME_WIDTH - (int) (backgroundImage[4].getWidth() * 1.5), (int) (GAME_HEIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
        if (backgroundImage[3] != null)
            g.drawImage(backgroundImage[3], 0, (int) (GAME_HEIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);


    }

    // mất tiêu điểm bug
    public void windowFocusLost() {
        player.resetDirBoleans();
    }

    //getter Player
    public Player getPlayer() {
        return player;
    }
}
