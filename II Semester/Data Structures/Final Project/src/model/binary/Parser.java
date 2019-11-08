package model.binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.user.User;

public class Parser {
	
	private static Parser instancia = null;
	
	public static Parser getInstancia() {
		if (instancia == null) {
			instancia = new Parser();
		}
		return instancia;
	}
	
	// Crea un archivo vacio en el path especificado "...\\path\\path\\file.type"
	public File crearFile(String pPath) {
		File file = new File(pPath);
		
		if (file.exists()) {
			System.out.println("Existe");
			return file; // Retorna el File existente
		}
		
		try {
			file.createNewFile();
			return file; // Retorna el nuevo File creado
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Escribe el User en el archivo de 'user.bin'
	public void write(User pUser) {
		
		try {
			FileOutputStream fileOS = new FileOutputStream(".\\static\\users\\user.bin", true);
			ObjectOutputStream userOS = new ObjectOutputStream(fileOS);
			
			userOS.writeObject(pUser);
			userOS.flush();
			userOS.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void write(User pUser, String pPath) {
		
	}
	
	public void read() {
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(".\\static\\users\\user.bin"));
			boolean cont = true;
			try {
				while(cont){
				      Object obj = oIS.readObject();
				      if(obj != null)
				         System.out.println(obj);
				      else
				         cont = false;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		Parser test = Parser.getInstancia();
		
		test.write(new User("kenneth.sanchez0609@gmail.com", "AAA"));
		test.write(new User("gvskenneths.aasdasd@gmail.com", "AAAA"));
		test.write(new User("asdi4eiaasdasd@gmail.com", "3414AAAA"));
		test.write(new User("39194edkasd@gmail.com", "asidjasi"));
		test.write(new User("gvskennethssidajd@gmail.com", "i9qsefuj2"));
		
		test.read();
	}
	
}
