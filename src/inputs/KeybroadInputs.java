package inputs;

import gamestate.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static utilz.constants.Direction.*;
public class KeybroadInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeybroadInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override

    // A W D to move, J to attack

    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state){
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            }
            default -> {
                break;

            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){
            case MENU -> {
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            }
            default -> {
                break;

            }
        }
    }
}
