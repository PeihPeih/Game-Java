package UI;

import gamestate.Gamestate;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static utilz.constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.constants.UI.UrmButtons.URM_SIZE;
import static utilz.constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.constants.UI.VolumeButtons.VOLUME_HEIGHT;

public class AudioOptions {
    
	private SoundButtons musicButtons,sfxButtons;
    private VolumeButton volumeButton;
    private Game game;
    
    
    public AudioOptions(Game game) throws IOException {
    	this.game = game;
        createSoundButtons();
        createVolumeButtons();
    }
    
    private void createVolumeButtons() {
        int vX=(int)(340* Game.SCALE);
        int vY = (int)(335*Game.SCALE);
        volumeButton= new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
    }


    private void createSoundButtons() throws IOException {
        int soundX=(int)(465*Game.SCALE);//vi tri X cua nut
        int musicY=(int)(170*Game.SCALE);//vi tri Y cua music
        int sfxY=(int)(218*Game.SCALE);// vi tri Y cua sfx
        musicButtons = new SoundButtons(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButtons = new  SoundButtons(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
    }
    
    
    public void update(){
        musicButtons.update();
        sfxButtons.update();
        volumeButton.update();

    }
    public void draw(Graphics g){
        //butons
        musicButtons.draw(g);
        sfxButtons.draw(g);
        //volume Slider
        volumeButton.draw(g);
    }
    
    public void mouseDragged(MouseEvent e){
    	if (volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			volumeButton.ChangeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if(valueBefore != valueAfter)
				game.getAudioPlayer().setVolume(valueAfter);
		}
    }


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressd(MouseEvent e) {
        if(isIn(e,musicButtons))
            musicButtons.setMousePressed(true);
        else if(isIn(e,sfxButtons))
            sfxButtons.setMousePressed(true);
        else if(isIn(e,volumeButton))
            volumeButton.setMousePressed(true);
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,musicButtons)){
            if(musicButtons.isMousePressed()){
                musicButtons.setMuted(!musicButtons.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }

        }
        else if(isIn(e,sfxButtons)){
            if(sfxButtons.isMousePressed()){
                sfxButtons.setMuted(!sfxButtons.isMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }
        musicButtons.resetBools();
        sfxButtons.resetBools();
        volumeButton.resetBools();

    }
    public void mouseMoved(MouseEvent e) {
        musicButtons.setMouseOver(false);
        sfxButtons.setMouseOver(false);
        volumeButton.setMouseOver(false);
        //neu no ko nam trong pham vi
        if(isIn(e,musicButtons))
            musicButtons.setMouseOver(true);
        else if(isIn(e,sfxButtons))
            sfxButtons.setMouseOver(true);
        else if(isIn(e,volumeButton))
            volumeButton.setMouseOver(true);
    }
    private boolean isIn(MouseEvent e,PasuedButtons b ){//kiem tra xem co trong hitbox cua nut ko
        return b.getBounds().contains(e.getX(),e.getY());

    }

}
