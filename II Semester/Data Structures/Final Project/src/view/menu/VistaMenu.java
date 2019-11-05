package view.menu;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VistaMenu extends JFrame{
	
	JButton btnHostear, btnConectar;
	
	public VistaMenu() {
		this.getContentPane().setLayout(null);
		this.setSize(900, 800);
		this.setResizable(false);
		
		btnHostear = new JButton("Hostear Partida");
		btnHostear.setBounds(100, 100, 100, 100);
		this.add(btnHostear);
		
		
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		VistaMenu test = new VistaMenu();
	}
}
