package inputs;

import gamestate.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state){
            case PLAYING -> {
            gamePanel.getGame().getPlaying().mouseClicked(e);
            }
            case OPTION -> {
                gamePanel.getGame().getOption().mouseClicked(e);
            }
            default -> {
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state){
            case MENU -> {
                gamePanel.getGame().getMenu().mouseMoved(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseMoved(e);
            }
            case OPTION -> {
                gamePanel.getGame().getOption().mouseMoved(e);
                break;
            }
            default -> {

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state){
            case MENU -> {
                gamePanel.getGame().getMenu().mousePressd(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mousePressd(e);
            }
            case OPTION -> {
                gamePanel.getGame().getOption().mousePressd(e);
                break;
            }
            default -> {

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state){
            case MENU -> {
                gamePanel.getGame().getMenu().mouseReleased(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseReleased(e);
            }
            case OPTION -> {
                gamePanel.getGame().getOption().mouseReleased(e);
            }
            default -> {

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state){
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseDragged(e);
            }
            case OPTION -> {
                gamePanel.getGame().getOption().mouseDragged(e);
            }
            default -> {

            }
        }

    }


}
