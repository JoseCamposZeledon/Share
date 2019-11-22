package controller.loginAccount;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

import controller.menu.MenuPrincipalController;
import model.account.Account;

public final class LogInEvent extends MouseAdapter {
	
	private LoginController controller;
	
	public LogInEvent(LoginController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		String correo = controller.getVista().getUserInput().getText().trim();
		String clave = controller.getVista().getPassInput().getText().trim();
		
		if (correo.length() == 0 || clave.length() == 0) {
			showError("No pueden haber campos vacios");
			return;
		}
		
		Account cuentaLogIn = new Account(correo, clave);
		
		try {
			controller.getModelo().validarMail(cuentaLogIn);
		} catch (AddressException exception) {
			showError("Usuario invalido");
			return;
		}
		
		Account cuentaEnArchivo = controller.getModelo().getAccountByPos(controller
				.getModelo().binarySearch(cuentaLogIn));
		
		if (cuentaEnArchivo == null) {
			showError("No existe esa cuenta");
			return;
		} 
		
		String passArchivo = String.copyValueOf(cuentaEnArchivo.getPassword()).trim(); 
		String passLogIn = String.copyValueOf(cuentaLogIn.getPassword()).trim();
		
		if (passArchivo.equals(passLogIn)) {
			controller.getVista().dispose();
			
			MenuPrincipalController newController = new MenuPrincipalController(cuentaLogIn);
		} else {
			showError("Password incorrecta");
		}
		
	}
	
	public void showError(String pMessage) {
		controller.getVista().getUserInput().setText("");
		controller.getVista().getPassInput().setText("");
		JOptionPane.showMessageDialog(null, pMessage);
	}
	
}
