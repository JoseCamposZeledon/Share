package view.login;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import view.IConstants;

public class VistaLogIn extends JFrame implements IConstants{
	private JLabel background, logo;
	
	private JTextField userInput; 
	private JPasswordField passInput;
	private JButton btnRegistrar, btnLogIn;
	
	public VistaLogIn() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO_MENU, ANCHO_MENU);
		this.setTitle("PROYECTO FINAL");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		userInput = new JTextField();
		userInput.setBounds(295, 250, 400, 20);
		this.add(userInput);
		
		passInput = new JPasswordField();
		passInput.setBounds(295, 280, 400, 20);
		this.add(passInput);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(295, 310, 195, 20);
		this.add(btnLogIn);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(500, 310, 195, 20);
		this.add(btnRegistrar);
		
		logo = new JLabel();
		logo.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\logo.png")
				.getImage().getScaledInstance(LOGO_LARGO, LOGO_ANCHO, Image.SCALE_SMOOTH)));
		logo.setBounds(220, 10, 550, 300);
		this.add(logo);
		
		background = new JLabel();
		background.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\background_login.gif")
				.getImage().getScaledInstance(LARGO_MENU, ANCHO_MENU, Image.SCALE_DEFAULT)));
		background.setBounds(0, 0, LARGO_MENU, ANCHO_MENU);
		this.add(background);
		
		this.setVisible(true);
	}
	
	
	
	public JTextField getUserInput() {
		return userInput;
	}



	public void setUserInput(JTextField userInput) {
		this.userInput = userInput;
	}



	public JTextField getPassInput() {
		return passInput;
	}


	public void setPassInput(JPasswordField passInput) {
		this.passInput = passInput;
	}



	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}



	public JButton getBtnLogIn() {
		return btnLogIn;
	}
}
