package UI;

import gamestate.Gamestate;
import gamestate.Playing;
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
    private SoundButtons musicButtons,sfxButtons;
    private UrmButtons menuB,replayB,unpauseB;
    private VolumeButton volumeButton;
    private Playing playing;

    public PauseOverlay(Playing playing) throws IOException {
        this.playing=playing;
        loadMenuBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButtons();
    }

    private void createVolumeButtons() {
        int vX=(int)(340*Game.SCALE);
        int vY = (int)(335*Game.SCALE);
        volumeButton= new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
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

    private void createSoundButtons() throws IOException {
        int soundX=(int)(465*Game.SCALE);//vi tri X cua nut
        int musicY=(int)(170*Game.SCALE);//vi tri Y cua music
        int sfxY=(int)(218*Game.SCALE);// vi tri Y cua sfx
        musicButtons = new SoundButtons(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButtons = new  SoundButtons(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
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
                musicButtons.update();
                sfxButtons.update();

                menuB.update();
                replayB.update();
                unpauseB.update();

                volumeButton.update();
    }
    public void draw(Graphics g){
        //Option background
        g.drawImage(OptionBackground,bgX,bgY,bgW,bgH,null);
        //butons
        musicButtons.draw(g);
        sfxButtons.draw(g);
        //urmbuttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        //volume Slider
        volumeButton.draw(g);

    }
    public void mouseDragged(MouseEvent e){
        if(volumeButton.isMousePressed()){
            volumeButton.ChangeX(e.getX());
        }
    }


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressd(MouseEvent e) {
        if(isIn(e,musicButtons))
            musicButtons.setMousePressed(true);
        else if(isIn(e,sfxButtons))
            sfxButtons.setMousePressed(true);
        else if(isIn(e,menuB))
            menuB.setMousePressed(true);
        else if(isIn(e,replayB))
            replayB.setMousePressed(true);
        else if(isIn(e,unpauseB))
            unpauseB.setMousePressed(true);
        else if(isIn(e,volumeButton))
            volumeButton.setMousePressed(true);
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,musicButtons)){
            if(musicButtons.isMousePressed()){
                musicButtons.setMuted(!musicButtons.isMuted());
            }

        }
        else if(isIn(e,sfxButtons)){
            if(sfxButtons.isMousePressed()){
                sfxButtons.setMuted(!sfxButtons.isMuted());
            }
        }
        else if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                Gamestate.state=Gamestate.MENU;
                playing.unpausedGame();
                playing.resetALL();
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
        musicButtons.resetBools();
        sfxButtons.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();

    }
    public void mouseMoved(MouseEvent e) {
        musicButtons.setMouseOver(false);
        sfxButtons.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);
        //neu no ko nam trong pham vi
        if(isIn(e,musicButtons))
            musicButtons.setMouseOver(true);
        else if(isIn(e,sfxButtons))
            sfxButtons.setMouseOver(true);
        else if(isIn(e,menuB))
            menuB.setMouseOver(true);
        else if(isIn(e,replayB))
            replayB.setMouseOver(true);
        else if(isIn(e,unpauseB))
            unpauseB.setMouseOver(true);
        else if(isIn(e,volumeButton))
            volumeButton.setMouseOver(true);
    }
    private boolean isIn(MouseEvent e,PasuedButtons b ){//kiem tra xem co trong hitbox cua nut ko
            return b.getBounds().contains(e.getX(),e.getY());

    }


}
