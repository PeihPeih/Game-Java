package main;


import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import gamestate.Gamestate;
import gamestate.Menu;
import gamestate.Playing;
import utilz.LoadSave;


public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private Random random = new Random();
    private Gamestate gamestate;
    private Menu menu;
    private Playing playing;

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
    public Game() throws IOException {

        innitClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();        // yeu cau cac input di vao game panel
        startGameLoop();
    }



    // khởi tạo 1 đối tượng nh player, enemy,..
    private void innitClasses() throws IOException {
        menu = new Menu(this);
        playing = new Playing(this);
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
    public void update() {
        switch (Gamestate.state){//Update tung trang thai khac nhau
            case MENU -> {
                menu.update();
                break;
            }
            case PLAYING -> {
                playing.update();
                break;
            }
            case OPTION -> {

            }
            case QUIT -> {
                System.exit(0);
            }
            default -> {
                break;
            }
        }
    }

    // In ra screen
    public void render(Graphics g) {
        switch (Gamestate.state){//render tung trang thai khi nao den phan nao thì mới render
            case MENU -> {
                menu.draw(g);
                break;
            }
            case PLAYING -> {
                playing.draw(g);
                playing.destroy();
                break;
            }
            default -> {
                break;
            }
        }

    }
    // mất tiêu điểm bug
    public void windowFocusLost() {// nếu bắt đầu chơi thì để trạng thái nhân vật mặc định
        if(Gamestate.state== Gamestate.PLAYING)
            playing.getPlayer().resetDirBoleans();

    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }

}
