package model.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable, Comparable<User>, IConstants{
	
	private char[] user = new char[USER_SIZE]; // 200 Bytes
	private char[] password = new char[PASSWORD_SIZE]; // 60 Bytes
	private int counterVictorias; // 4 Bytes
	
	
	public User(String pUser, String pPassword) {
		
			user = toChar(pUser, USER);
			setPassword(toChar(pPassword, PASSWORD));
			
			for (int i = 0; i < USER_SIZE; i++) {
				System.out.println(user[i]);
			}
			
			counterVictorias = 0;
	}
	
	// Transforma un string a un array de chars, dependiendo del codigo 
	public char[] toChar(String pString, int pCode) {
		char[] charArray = null;
		
		if (pCode == USER) { 
			charArray = new char[USER_SIZE];
		} else if (pCode == PASSWORD) {
			charArray = new char[PASSWORD_SIZE];
		}
 		
		return charArray;
	}
	
	// Transforma la instancia en un array de bytes
	public byte[] toByte() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			oos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bos.toByteArray();
	}
	
	public int getCounterVictorias() {
		return counterVictorias;
	}
	
	public String toString() {
		return "Correo: " + user.toString() + " | Victorias: " + counterVictorias;
	}
	
	@Override
	public int compareTo(User o) {
		String stringThis = "";
		String stringO = "";
		
		for (int i = 0; i < USER_SIZE; i++) {
			stringThis += this.getUser()[i];
			stringO += this.getUser()[i];
		}
		
		return stringThis.compareTo(stringO);
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}
	
	public char[] getUser() {
		return user;
	}

	public void setUser(char[] user) {
		this.user = user;
	}

	
}
