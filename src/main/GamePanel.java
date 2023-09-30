package main;

import inputs.KeybroadInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.constants.PlayerConstants.*;
import static utilz.constants.Direction.*;

public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
	private Game game;

	// Khởi tạo
    public GamePanel(Game game){

        setPanelSize();
        mouseInputs = new MouseInputs(this);
        this.game = game;

        addKeyListener(new KeybroadInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


	// Cài đặt width x height
	private void setPanelSize() {
		Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setPreferredSize(size);
	}

    public Game getGame(){
        return game;
    }

	// In ra screen
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		game.render(g);
	}

}
