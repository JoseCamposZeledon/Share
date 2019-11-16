package controller.loginAccount;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

import model.account.Account;
import model.binary.ExistingUserException;

public final class SignUpEvent extends MouseAdapter {
	
	LoginController controller;
	
	public SignUpEvent(LoginController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		String correo = controller.getVista().getUserInput().getText().trim();
		String clave = controller.getVista().getPassInput().getText().trim();
		
		if (correo.length() == 0 || clave.length() == 0) {
			showError("No pueden haber campos vacios");
			return;
		}
		
		Account nuevaCuenta = new Account(correo, clave);
		
		try {
			controller.getModelo().validarMail(nuevaCuenta);
			controller.getModelo().write(nuevaCuenta);
		} catch (AddressException exception) {
			showError("Usuario invalido");
			return;
		} catch (ExistingUserException e1) {
			showError("Ya existe ese usuario");
			return;
		}
		
		controller.getModelo().rewriteAccounts();
		
		controller.getVista().getUserInput().setText("");
		controller.getVista().getPassInput().setText("");
		JOptionPane.showMessageDialog(null, "Cuenta creada, proceda a hacer Log In");
		
		controller.getModelo().readAll();
	}
	
	public void showError(String pMessage) {
		controller.getVista().getUserInput().setText("");
		controller.getVista().getPassInput().setText("");
		JOptionPane.showMessageDialog(null, pMessage);
	}
	
	
}
