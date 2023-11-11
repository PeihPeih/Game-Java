package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.constants.UI.PauseButtons.*;

public class SoundButtons extends PasuedButtons {
    
	private BufferedImage[][] soundImgs;
    private boolean mouseOver,mousePressed;
    private boolean muted;
    private int rowIndex,colIndex;
    
    
    public SoundButtons(int x, int y, int width,int Height) throws IOException {
        super(x, y, width, Height);
        loadSoundImgs();
    }

    private void loadSoundImgs() throws IOException {
        soundImgs = new BufferedImage[2][3];
        for(int j=0;j<soundImgs.length;j++){
            for (int i=0;i<soundImgs[j].length;i++){
                try {
                    BufferedImage temp =  ImageIO.read(getClass().getResourceAsStream("/UI/Option/Volume.png"));
                    soundImgs[j][i] = temp.getSubimage(i*SOUND_SIZE_DEFAULT,j*SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void update(){
        if(muted)
            rowIndex=1;
        else
            rowIndex=0;
        colIndex=0;
        if(mouseOver)
            colIndex=1;
        if (mousePressed)
            colIndex=2;
    }
    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
    }
    public void draw(Graphics g){
        g.drawImage(soundImgs[rowIndex][colIndex],x,y,width,height,null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
