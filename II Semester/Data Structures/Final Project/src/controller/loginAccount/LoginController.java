package controller.loginAccount;

import model.binary.UserParser;
import view.login.VistaLogIn;

public class LoginController {
	
	UserParser modelo;
	VistaLogIn vista;
	
	public LoginController () {
		modelo = UserParser.getInstancia();
		vista = new VistaLogIn();
		
		vista.getBtnLogIn().addMouseListener(new LogInEvent());
		vista.getBtnRegistrar().addMouseListener(new SignUpEvent());
	}
	
	
	
	public static void main(String[] args) {
		LoginController test = new LoginController();
	}
}
