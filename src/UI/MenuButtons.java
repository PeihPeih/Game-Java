package UI;

import gamestate.Gamestate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.constants.UI.Buttons.*;

public class MenuButtons {
    private int xPos, yPos, rowIndex,index;//xPos , Ypos vi tri cua nut, rowIndex la hang( xem anh cua phan UI Menu)

    private int xOffsetCenter=B_WIDTH/2;//
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;

    public MenuButtons(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBouds();
    }

    private void initBouds() {// tao ra hit box cho nut
        bounds = new Rectangle(xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT);
    }

    private void loadImgs() {// load anh nut
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            try {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/UI/Menu/Buttons.png"));
                imgs[i] = temp.getSubimage(i*B_WIDTH_DEFAULT,rowIndex*B_HEIGHT_DEFAULT,B_WIDTH_DEFAULT,B_HEIGHT_DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void draw(Graphics g){//ve
        g.drawImage(imgs[index],xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT,null);
    }
    public void update(){// update neu co di chuyen den
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void applyGameState(){
        Gamestate.state= state;
    }
    public void resetBools(){//reset lai trang thai cua nut
        mouseOver = false;
        mousePressed = false;
    }
    
}
