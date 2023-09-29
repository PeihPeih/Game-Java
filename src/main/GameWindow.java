package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel)
	{
		jframe = new JFrame();

		jframe.add(gamePanel);// them game panel vao khung window (nhu lap tranh vao khung tranh)
		jframe.setResizable(false); // K có nút điều chinh size window
		jframe.pack(); // Fit the size of the window to the prefered size of the component
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tạo nút exit (dấu X) ở góc

		jframe.setLocationRelativeTo(null);        			   // dat game ve giua man hinh khi bat len

		jframe.setVisible(true);	 						   // hien thi man hinh game (dat o cuoi de tranh loi)
	}
}
