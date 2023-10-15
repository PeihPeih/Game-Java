package gamestate;

import UI.MenuButtons;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.constants.UI.Buttons.B_HEIGHT_DEFAULT;
import static utilz.constants.UI.Buttons.B_WIDTH_DEFAULT;

public class Menu extends State implements Statemethods {

    private MenuButtons[] buttons = new MenuButtons[3];

    private BufferedImage[] backgroundImage;

    private int menuX,menuY,menuWidth,menuHeight;
    private BufferedImage MenuBackGround;
    private void loadBackground() {//ve background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
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
    }

    public void loadButtons(){//load nut ( boi vi cai Menu buttons chi la cai rieng biet de load tung nut)
            buttons[0] = new MenuButtons(Game.GAME_WIDTH/2, (int) (200*Game.SCALE),0,Gamestate.PLAYING);
            buttons[1] = new MenuButtons(Game.GAME_WIDTH/2, (int) (270*Game.SCALE),1,Gamestate.OPTION);
            buttons[2] = new MenuButtons(Game.GAME_WIDTH/2, (int) (340*Game.SCALE),2,Gamestate.QUIT);
    }
    public void loadMenuBackground(){//load anh menu
        try {
                MenuBackGround = ImageIO.read(getClass().getResourceAsStream("/UI/Menu/Menu.png"));
                menuWidth=(int)(MenuBackGround.getWidth()*Game.SCALE);
                menuHeight=(int)(MenuBackGround.getHeight()*Game.SCALE);
                menuX=Game.GAME_WIDTH/2 -menuWidth/2;
                menuY=(int)(70*Game.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Menu(Game game) {
        super(game);
        innitClasses();
    }

    private void innitClasses() {//khoi tao
        loadBackground();
        loadButtons();
        loadMenuBackground();
    }

    @Override
    public void update() {//update
        for (MenuButtons mb : buttons){
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {//ve
        drawBackground(g);
        g.drawImage(MenuBackGround,menuX,menuY,menuWidth,menuHeight,null);
        for (MenuButtons mb : buttons){
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressd(MouseEvent e) {// neu nhan vao thi cai mousepressd ben menubuttos no se bang true va chay hoat anh
        for (MenuButtons mb : buttons){
            if(IsIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {// tha con chuot ra neu cai press = true thi chuyen trang thai
        for (MenuButtons mb : buttons){
            if(IsIn(e,mb)){
                if(mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {//reset lai khi con tro chuot ko o
        for (MenuButtons mb : buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {//neu ma con chuot dang trong hitbox thi mouseover = fasle , ko thi no = true
        for (MenuButtons mb : buttons){
           mb.setMouseOver(false);
        }
        for (MenuButtons mb : buttons){
            if(IsIn(e,mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {// nếu nhấn enter thì đổi trạng thái mặc định là menu thành playing

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
