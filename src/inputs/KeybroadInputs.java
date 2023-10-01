package inputs;

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

    // vào game panel  -> lấy class game -> vaof game -> lấy class player (chỉnh sửa direction và moving )
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setDirection(RIGHT);
                break;
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setDirection(UP);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // thả key thì k di chuyển
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setMoving(false);
                break;
        }
    }
}
