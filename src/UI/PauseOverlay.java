package UI;

import gamestate.Gamestate;
import gamestate.Playing;
import levels.LevelManager;
import main.Game;
import utilz.constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.constants.UI.PauseButtons.*;
import static utilz.constants.UI.UrmButtons.*;
import static utilz.constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private BufferedImage OptionBackground;
    private  int bgX,bgY,bgW,bgH;
    private UrmButtons menuB,replayB,unpauseB;
    private AudioOptions audioOptions;
    private Playing playing;

    public PauseOverlay(Playing playing) throws IOException {
        this.playing=playing;
        loadMenuBackground();
        createUrmButtons();
        audioOptions = playing.getGame().getAudioOptions();
    }



    private void createUrmButtons() {
        int menuX=(int)(350*Game.SCALE);
        int replayX = (int)(422*Game.SCALE);
        int unpasuseX=(int)(492*Game.SCALE);
        int bY=(int)(390*Game.SCALE);
        menuB=new UrmButtons(menuX,bY,URM_SIZE,URM_SIZE,0);
        replayB=new UrmButtons(replayX,bY,URM_SIZE,URM_SIZE,2);
        unpauseB=new UrmButtons(unpasuseX,bY,URM_SIZE,URM_SIZE,1);
    }

    public void loadMenuBackground(){//load anh menu
        try {
            OptionBackground = ImageIO.read(getClass().getResourceAsStream("/UI/Option/Option.png"));
            bgW=(int)(OptionBackground.getWidth()* Game.SCALE);
            bgH=(int)(OptionBackground.getHeight()*Game.SCALE);
            bgX=Game.GAME_WIDTH/2 -bgW/2;
            bgY=(int)(50*Game.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void update(){
                menuB.update();
                replayB.update();
                unpauseB.update();
                audioOptions.update();

    }
    public void draw(Graphics g){
        //Option background
        g.drawImage(OptionBackground,bgX,bgY,bgW,bgH,null);
        //urmbuttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        //audioOptions
        audioOptions.draw(g);

    }
    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressd(MouseEvent e) {
        if(isIn(e,menuB))
            menuB.setMousePressed(true);
        else if(isIn(e,replayB))
            replayB.setMousePressed(true);
        else if(isIn(e,unpauseB))
            unpauseB.setMousePressed(true);
        else audioOptions.mousePressd(e);
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                playing.resetLvl();
                playing.unpausedGame();
                playing.resetTime();
                Gamestate.state=Gamestate.MENU;
            }
        }
        else if(isIn(e,replayB)){
            if(replayB.isMousePressed()){
                playing.resetALL();
                playing.unpausedGame();
            }
        }
        else if(isIn(e,unpauseB)){
            if(unpauseB.isMousePressed()){
                playing.unpausedGame();
            }
        }
        else audioOptions.mouseReleased(e);

        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();

    }
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        //neu no ko nam trong pham vi
        if(isIn(e,menuB))
            menuB.setMouseOver(true);
        else if(isIn(e,replayB))
            replayB.setMouseOver(true);
        else if(isIn(e,unpauseB))
            unpauseB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }
    private boolean isIn(MouseEvent e,PasuedButtons b ){//kiem tra xem co trong hitbox cua nut ko
            return b.getBounds().contains(e.getX(),e.getY());

    }


}
