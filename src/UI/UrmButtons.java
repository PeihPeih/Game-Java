package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.constants.UI.UrmButtons.*;

public class UrmButtons extends PasuedButtons  {
    private BufferedImage[] Imgs;
    private int rowIndex,index;
    private boolean mouseOver, mousePressed;
    public UrmButtons(int x, int y, int width, int height,int rowIndex) {
        super(x, y, width, height);
        this.rowIndex=rowIndex;
        loadImgs();
    }

    private void loadImgs() {//load anh tu urm_buttons
        Imgs = new BufferedImage[3];
        for(int j=0;j<Imgs.length;j++){
                try {
                    BufferedImage temp =  ImageIO.read(getClass().getResourceAsStream("/UI/Option/Buttons.png"));
                    Imgs[j] = temp.getSubimage(j*URM_SIZE_DEFAULT,rowIndex*URM_SIZE_DEFAULT,URM_SIZE_DEFAULT,URM_SIZE_DEFAULT);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void update(){
        index=0;
        if(mouseOver)
            index=1;
        if (mousePressed)
            index=2;

    }
    public void draw(Graphics g){
        g.drawImage(Imgs[index],x,y,URM_SIZE,URM_SIZE,null);
    }
    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
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
}
