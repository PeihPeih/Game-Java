package gamestate;

import UI.MenuButtons;
import audio.AudioPlayer;
import main.Game;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game=game;
    }
    public boolean IsIn(MouseEvent e , MenuButtons mb){//xem cai vi tri con tro chuot co trong hit box cua buttons ko
        return mb.getBounds().contains(e.getX(),e.getY());
    }
    public Game getGame(){
        return game;
    }
    
    public void setGamestate(Gamestate state)
    {
    	switch(state)
    	{
    	case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU);
    	case PLAYING -> game.getAudioPlayer().playSong(AudioPlayer.PLAY);
    	}
    	Gamestate.state = state;
    }
}
