package gamestate;

import UI.AudioOptions;
import UI.PasuedButtons;
import UI.UrmButtons;
import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

import static utilz.constants.UI.UrmButtons.URM_SIZE;

public class Option extends State implements Statemethods {
    private BufferedImage[] backgroundImage;
    private BufferedImage cloud;
    private int scrollCloud;
    AudioOptions audioOptions;
    private BufferedImage OptionBackground;

    private  int bgX,bgY,bgW,bgH;
    private UrmButtons menuB;


    public Option(Game game) {
        super(game);
        audioOptions = game.getAudioOptions();
        createUrmButtons();
        loadMenuBackground();
        loadBackground();
    }
    private void loadBackground() {//ve background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
        cloud = LoadSave.GetSpriteAtlas(LoadSave.CLOUD);

    }

    private void drawBackground(Graphics g) {
        if (backgroundImage[0] != null) g.drawImage(backgroundImage[0], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        if (backgroundImage[1] != null) g.drawImage(backgroundImage[1], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        if (backgroundImage[2] != null)
            g.drawImage(backgroundImage[2], 0, (int) (Game.GAME_HEIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
        if (backgroundImage[4] != null)
            g.drawImage(backgroundImage[4], Game.GAME_WIDTH - (int) (backgroundImage[4].getWidth() * 1.5), (int) (Game.GAME_HEIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
        if (backgroundImage[3] != null)
            g.drawImage(backgroundImage[3], 0, (int) (Game.GAME_HEIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);
        for(int i=0;i<2;i++){
            if(cloud!=null){
                g.drawImage(cloud,i*Game.GAME_WIDTH+scrollCloud,-150,Game.GAME_WIDTH,400,null);
            }
        }
    }
    private void createUrmButtons() {
        int menuX=(int)(419*Game.SCALE);
        int bY=(int)(390*Game.SCALE);
        menuB=new UrmButtons(menuX,bY,URM_SIZE,URM_SIZE,0);
    }

    public void loadMenuBackground(){//load anh menu
        try {
            OptionBackground = ImageIO.read(getClass().getResourceAsStream("/UI/Option/Option.png"));
            bgW=(int)(OptionBackground.getWidth()* Game.SCALE);
            bgH=(int)(OptionBackground.getHeight()*Game.SCALE);
            bgX=Game.GAME_WIDTH/2 -bgW/2;
            bgY=(int)(50*Game.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
            drawBackground(g);
            g.drawImage(OptionBackground,bgX,bgY,bgW,bgH,null);
            audioOptions.draw(g);
            menuB.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressd(MouseEvent e) {
        if(isIn(e,menuB))
            menuB.setMousePressed(true);
        else audioOptions.mousePressd(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                Gamestate.state=Gamestate.MENU;
            }
        }
        else
            audioOptions.mouseReleased(e);

        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        //neu no ko nam trong pham vi
        if(isIn(e,menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private boolean isIn(MouseEvent e, PasuedButtons b ){//kiem tra xem co trong hitbox cua nut ko
        return b.getBounds().contains(e.getX(),e.getY());

    }

}
