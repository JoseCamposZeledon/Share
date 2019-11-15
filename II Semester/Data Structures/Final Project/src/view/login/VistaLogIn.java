package view.login;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.IConstants;

public class VistaLogIn extends JFrame implements IConstants{
	JLabel background;
	
	JTextField userInput, passInput;
	JButton btnRegistrar, btnLogIn;
	
	public VistaLogIn() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO, ANCHO);
		this.setTitle("PROYECTO FINAL");
		this.setResizable(false);
		
		userInput = new JTextField();
		userInput.setBounds(300, 200, 400, 20);
		this.add(userInput);
		
		passInput = new JTextField();
		passInput.setBounds(300, 230, 400, 20);
		this.add(passInput);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(300, 260, 195, 20);
		this.add(btnLogIn);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(505, 260, 195, 20);
		this.add(btnRegistrar);
		
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
