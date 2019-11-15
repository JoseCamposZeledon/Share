package model.binary;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.lang.instrument.Instrumentation;

import model.user.Account;

public class UserParser {

	private static final String BINARIES_PATH = ".\\static\\users";
	
	private static UserParser instancia = null;

	private static File usersFile; 
	
	private UserParser() {
		usersFile = crearArchivo("usuarios.bin");
	}
	
	public static UserParser getInstancia() {
		if (instancia == null) {
			instancia = new UserParser();
		}
		return instancia;
	}
	
	// Crea un nuevo archivo archivo, retorna el existe si ya existe
	@SuppressWarnings("finally")
	public static File crearArchivo(String pNombreArchivo) {
		String tempPath = BINARIES_PATH + "\\" + pNombreArchivo;
		File nuevoArchivo = new File(tempPath);
		
		// Archivo existe
		if (nuevoArchivo.exists()) return nuevoArchivo;
		
		// Crea uno nuevo
		try {
			nuevoArchivo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return nuevoArchivo;
		}
	}
	
	
	// Escribe un nuevo usuario en usersFile
	public void write(Account pUser) {
		
		// Revisa si el usuario ya esta en el archivo
		if (!fileIsEmpty()) {
			if (binarySearch(pUser)) {
				return;
			}
		}
		
		// Se escribe el nuevo usuario en el archivo y se ordena 
		try {
			FileOutputStream fileOS;
			ObjectOutputStream userOS; 
			
			if (!fileIsEmpty()) { // Para instancia append, para que no se corrompa el archivo
				fileOS = new FileOutputStream(usersFile, true);
				userOS = new AppendingObjectOutputStream(fileOS);
			} else { // Instancia normal para el primer objeto que se escribe
				 fileOS = new FileOutputStream(usersFile);
				 userOS = new ObjectOutputStream(fileOS);
			}
			
			userOS.flush();
			userOS.writeObject(pUser);
			
			mezclaNatural();
			
			userOS.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO - Busqueda Binaria
	public boolean binarySearch(Account pUser) {
		try {
			FileInputStream FileIS = new FileInputStream(usersFile);
			ObjectInputStream OIS = new ObjectInputStream(FileIS);
			Object obj = OIS.readObject();
			if (pUser.getCorreo().equals(((Account) obj).getCorreo())) {
				System.out.println(pUser.getCorreo() + " | " + ((Account) obj).getCorreo());
				return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean fileIsEmpty() {
		return usersFile.length() == 0;
	}
	
	public void readAll() {
		boolean finArchivo = false;
		FileInputStream FileIS;
		try {
			FileIS = new FileInputStream(usersFile);
			ObjectInputStream OIS = new ObjectInputStream(FileIS);
			
			while (!finArchivo) {
				
				try {
					System.out.println("READING: " + OIS.readObject());
				} catch (EOFException e) {
					finArchivo = true;
				}
			}
			
			OIS.close();
			if (this.eliminarArchivo(usersFile)) System.out.println("Eliminado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
 	
	public void mezclaNatural() {
		
	}
	
	public boolean eliminarArchivo(File pFile) {
		return pFile.delete();
	}
	
	public static void main(String[] args) {
		
		UserParser test = UserParser.getInstancia();
		Account test1 = new Account("aa@aa.com", "134");
		Account test2 = new Account("aa32@aa.com", "131313");
		Account test3 = new Account("asda@a.net", "134jasijdak asd");
		test.write(test1);
		test.write(test2);
		test.write(test3);
		test.readAll();
	}
	
}
