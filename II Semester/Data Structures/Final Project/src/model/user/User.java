package model.user;

import java.io.Serializable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class User implements Serializable, Comparable<User>{
	
	private InternetAddress correo;
	private String password;
	private int counterVictorias;
	
	
	public User(String pCorreo, String pPassword) {
		
		try {
			correo = new InternetAddress(pCorreo);
			correo.validate();
			
			password = pPassword;
			
			counterVictorias = 0;
			
		} catch (AddressException e) {
		
			System.out.println("Correo invalido");
			
		}		
	}
	
	public InternetAddress getCorreo() {
		return correo;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	
	public int getCounterVictorias() {
		return counterVictorias;
	}
	
	public String toString() {
		return "Correo: " + correo + " | Victorias: " + counterVictorias;
	}
	
	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.getCorreo().toString().compareTo(o.getCorreo().toString());
	}
	
}
