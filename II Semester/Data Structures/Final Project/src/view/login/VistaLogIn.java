package view.login;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.IConstants;

public class VistaLogIn extends JFrame implements IConstants{
	JLabel background, logo;
	
	JTextField userInput, passInput;
	JButton btnRegistrar, btnLogIn;
	
	public VistaLogIn() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO, ANCHO);
		this.setTitle("PROYECTO FINAL");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		userInput = new JTextField();
		userInput.setBounds(275, 230, 400, 20);
		this.add(userInput);
		
		passInput = new JTextField();
		passInput.setBounds(275, 260, 400, 20);
		this.add(passInput);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(275, 290, 195, 20);
		this.add(btnLogIn);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(480, 290, 195, 20);
		this.add(btnRegistrar);
		
		logo = new JLabel();
		logo.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\logo.png")
				.getImage().getScaledInstance(550, 200, Image.SCALE_SMOOTH)));
		logo.setBounds(200, 10, 550, 300);
		this.add(logo);
		
		background = new JLabel();
		background.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\background.gif")
				.getImage().getScaledInstance(LARGO, ANCHO, Image.SCALE_DEFAULT)));
		background.setBounds(0, 0, LARGO, ANCHO);
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

	
	public void asd() {
		System.out.println("aaa");
	}
	

	public void setPassInput(JTextField passInput) {
		this.passInput = passInput;
	}



	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}



	public JButton getBtnLogIn() {
		return btnLogIn;
	}
}
