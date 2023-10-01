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

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            // Move
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(true);
                break;

                // Attack
            case KeyEvent.VK_J:
                gamePanel.getGame().getPlayer().setAttack(true);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // thả key thì k di chuyển
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_J:
                gamePanel.getGame().getPlayer().setAttack(false);
                break;
        }
    }
}
