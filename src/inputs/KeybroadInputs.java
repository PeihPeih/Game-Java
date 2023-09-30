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

    // Code lại chỗ này nhé. Code xong xóa cmt này đi
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:

            case KeyEvent.VK_S:

            case KeyEvent.VK_D:

            case KeyEvent.VK_W:

        }
    }

    // Code lại chỗ này nhé. Code xong xóa cmt này đi
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:

                break;
            case KeyEvent.VK_S:

                break;
            case KeyEvent.VK_D:

                break;
            case KeyEvent.VK_W:

                break;
        }
    }
}
