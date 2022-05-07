package components;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
public class MainFrame extends JFrame{

	public MainFrame( int sizex ,int sizey ,int marginx ,int marginy ) {
		
		setLayout(new BorderLayout(marginx,marginy));
		setSize(new Dimension(sizex,sizey));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
