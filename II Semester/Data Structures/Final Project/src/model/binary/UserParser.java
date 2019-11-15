package model.binary;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import model.account.Account;

public class UserParser implements IConstants{
	
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
		write(pUser, usersFile);
	}
	
	
	// Escribe un nuevo usuario en el file especificado
	public void write(Account pUser, File pFile) {
		
		// Revisa si el usuario ya esta en el archivo
		if (!fileIsEmpty(pFile)) {
			if (binarySearch(pUser)) {
				return;
			}
		}
		
		// Se escribe el nuevo usuario en el archivo y se ordena 
		try {
			FileOutputStream fileOS;
			ObjectOutputStream userOS; 
			
			if (!fileIsEmpty(pFile)) { // Instancia append, para que no se corrompa el archivo
				fileOS = new FileOutputStream(pFile, true);
				userOS = new AppendingObjectOutputStream(fileOS);
			} else { // Instancia normal para el primer objeto que se escribe
				 fileOS = new FileOutputStream(pFile, true);
				 userOS = new ObjectOutputStream(fileOS);
			}
			
//			System.out.println("WRITING POS: " + fileOS.getChannel().position());
			userOS.flush();
			userOS.writeObject(pUser);
			
//			mezclaNatural();
			
			userOS.close();
			fileOS.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean binarySearch(Account pBuscado) {		
		int izq = 0;
		int drch = getUserCount() - 1;
		
		try {
			FileInputStream fileIS = new FileInputStream(usersFile);
			ObjectInputStream oIS = new ObjectInputStream(fileIS);
			
			while (izq <= drch) {
				int medio = (izq + drch) / 2;
				// Mueve el puntero antes de leer el objeto guardado
				fileIS.getChannel().position(medio * ACCOUNT_BYTE_SIZE + OFFSET_INICIAL);
				
				Account accountActual = (Account) oIS.readObject();
				
				// Encuentra el indicado
				if (accountActual.compareTo(pBuscado) == 0) {
					System.out.println("ENCONTRADOS: " + accountActual + " - " + pBuscado);
					oIS.close();
					fileIS.close();
					return true;
				}
				
				// pBuscado es menor, se ignora la mitad mayor
				else if (accountActual.compareTo(pBuscado) < 0) {
					izq = medio + 1;
				}
				
				// pBuscado es mayor, se ignora la mitad menor
				else {
					drch = medio - 1;
				}	
			}
			
			fileIS.close();
			oIS.close();
			
			return false;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return false;
	}
	
	
	public boolean fileIsEmpty(File pFile) {
		return usersFile.length() == 0;
	}
	
	
	// Hace un print de todos los Objects en usuarios.bin
	public void readAll() {
		FileInputStream FileIS;
		
		try {
		
			FileIS = new FileInputStream(usersFile);
			ObjectInputStream OIS = new ObjectInputStream(FileIS);
			int counter = 0;
			
			while (counter < getUserCount()) {
				// Mueve al puntero de posicion hasta el proximo Account
				FileIS.getChannel().position(counter * ACCOUNT_BYTE_SIZE + OFFSET_INICIAL);
				System.out.println("READING: " + OIS.readObject());
				counter++;
			}
			
			OIS.close();
			FileIS.close();
			System.out.println("CANTIDAD DE USUARIOS: " + getUserCount());
//			if (this.eliminarArchivo(usersFile)) System.out.println("Eliminado");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
 	
	
	public void mezclaNatural() {
		File auxiliar1 = crearArchivo("auxiliar1.bin");
		File auxiliar2 = crearArchivo("auxiliar2.bin");
		File auxiliar3 = crearArchivo("auxiliar3.bin");
		
		
	}
	
	
	// Hay que asegurarse de hacer .close() en pFile antes de llamarlo
	public boolean eliminarArchivo(File pFile) {
		File filePath = pFile.getAbsoluteFile();
		
		Path absolutePath = filePath.toPath();
		pFile = null;

		try {
			if (Files.deleteIfExists(absolutePath)) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return false;
	}
	
	
	public int getUserCount() {
		return (int) Math.floor(usersFile.length() / ACCOUNT_BYTE_SIZE);
	}
	
	
	public static void main(String[] args) {
		
		UserParser test = UserParser.getInstancia();
		Account test1 = new Account("a2@aa.com", "134jasijdak asd");
		Account test2 = new Account("aa@aa.com", "134");
		Account test3 = new Account("aa3@aa.com", "131313");
		
		test.write(test1);
		test.write(test2);
		test.write(test3);
		test.readAll();
	}
	
}
