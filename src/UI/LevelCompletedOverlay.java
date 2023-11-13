package UI;

import gamestate.Gamestate;
import gamestate.Playing;
import main.Game;

import javax.imageio.ImageIO;

import audio.AudioPlayer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.constants.UI.UrmButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButtons menu,next;
    private BufferedImage[] imgs;
    private int bgX,bgY,bgW,bgH;
    private int aniTick,aniIndex,aniSpeed=3;
    private boolean check = false;
    public LevelCompletedOverlay(Playing playing){
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX=(int)(360 * Game.SCALE);
        int nextX=(int)(480*Game.SCALE);
        int y=(int)(220*Game.SCALE);
        next = new UrmButtons(nextX,y,URM_SIZE,URM_SIZE,1);
        menu = new UrmButtons(menuX,y,URM_SIZE,URM_SIZE,0);
    }

    private void initImg() {
        imgs = new BufferedImage[7];
        for(int j=0;j<imgs.length;j++){
            try {
                BufferedImage temp =  ImageIO.read(getClass().getResourceAsStream("/UI/LevelCompleted/LevelCompleted.png"));
                imgs[j] = temp.getSubimage(j*350,0,350,200);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        bgW = (int)(350* Game.SCALE);
        bgH=(int)(200*Game.SCALE);
        bgX=Game.GAME_WIDTH/2 - bgW/2;
        bgY = (int) (100 * Game.SCALE);
    }
    private void updateanimationBg() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex==6)
            {
                check=true;
            }
        }
    }
    public void draw(Graphics g){

        if(!check){
            updateanimationBg();
            g.drawImage(imgs[aniIndex],bgX,bgY,bgW,bgH,null);
        }
        else
        {
            g.drawImage(imgs[aniIndex],bgX,bgY,bgW,bgH,null);
            next.draw(g);
            menu.draw(g);
        }
    }


    public void update(){
        next.update();
        menu.update();
    }
    private boolean isIn(UrmButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu,e))
            menu.setMouseOver(true);
        if(isIn(next,e))
            next.setMouseOver(true);
    }
    public void mouseReleased(MouseEvent e) throws IOException {
        if(isIn(menu,e))
            if(menu.isMousePressed()){
            	playing.getGame().getAudioPlayer().playSong(AudioPlayer.MENU);
                playing.resetLvl();
                playing.resetTime();
                Gamestate.state=Gamestate.MENU;
            }
        if(isIn(next,e))
            if(next.isMousePressed()){
            	playing.getGame().getAudioPlayer().playSong(AudioPlayer.PLAY);
                playing.loadNextlevel();
                playing.resetALL();
            }

        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e))
            menu.setMousePressed(true);
        if(isIn(next,e))
            next.setMousePressed(true);
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
        check=false;
    }
}
