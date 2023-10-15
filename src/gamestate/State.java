package gamestate;

import UI.MenuButtons;
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
}
