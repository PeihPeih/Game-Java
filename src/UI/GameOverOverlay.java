package UI;

import entities.Player;
import gamestate.Gamestate;
import gamestate.Playing;
import main.Game;

import javax.imageio.ImageIO;

import audio.AudioPlayer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.constants.UI.UrmButtons.URM_SIZE;

public class GameOverOverlay{
    Playing playing;
    private BufferedImage gameover;
    private int imgX, imgY, imgW, imgH;
    private UrmButtons menu, play;
    public GameOverOverlay(Playing playing) throws IOException {
        this.playing = playing;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX=(int)(350* Game.SCALE);
        int replayX = (int)(500*Game.SCALE);
        int bY=(int)(200*Game.SCALE);
        menu=new UrmButtons(menuX,bY,URM_SIZE,URM_SIZE,0);
        play=new UrmButtons(replayX,bY,URM_SIZE,URM_SIZE,2);
    }
    private void createImg() throws IOException {
        try {
            gameover = ImageIO.read(getClass().getResourceAsStream("/UI/GameOver/GameOver.png"));
            imgW=(int)(gameover.getWidth()* Game.SCALE);
            imgH=(int)(gameover.getHeight()*Game.SCALE);
            imgX=Game.GAME_WIDTH/2 -imgW/2;
            imgY=(int)(70*Game.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(gameover, imgX, imgY, imgW, imgH, null);

        menu.draw(g);
        play.draw(g);
    }
    public void update() {
        menu.update();
        play.update();
    }
    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(play, e))
            play.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetLvl();
                playing.setGamestate(Gamestate.MENU);
                playing.resetTime();
                Gamestate.state=Gamestate.MENU;
            }
        } else if (isIn(play, e))
            if (play.isMousePressed()) {
                playing.resetLvl();

                playing.setGamestate(Gamestate.PLAYING);

                playing.resetTime();

            }
        menu.resetBools();
        play.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(play, e))
            play.setMousePressed(true);
    }

}
