package controller.loginAccount;

import model.binary.UserParser;
import view.login.VistaLogIn;

public class LoginController {
	
	private UserParser modelo;
	private VistaLogIn vista;
	
	public LoginController () {
		modelo = UserParser.getInstancia();
		vista = new VistaLogIn();
		
		vista.getBtnLogIn().addMouseListener(new LogInEvent(this));
		vista.getBtnRegistrar().addMouseListener(new SignUpEvent(this));
	}
	
	
	
	public UserParser getModelo() {
		return modelo;
	}



	public VistaLogIn getVista() {
		return vista;
	}
}
