package model.binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
	public File crearArchivo(String pNombreArchivo) {
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
	public void write(Account pUser) throws ExistingUserException {
		write(pUser, usersFile);
	}
	
	
	// Escribe un nuevo usuario en el file especificado
	public void write(Account pUser, File pFile) throws ExistingUserException {
		
		// Revisa si el usuario ya esta en el archivo
		if (!fileIsEmpty(pFile)) {
			if (binarySearch(pUser, pFile) >= 0) {
				throw new ExistingUserException();
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

			userOS.flush();
			userOS.writeObject(pUser);
			
			userOS.close();
			fileOS.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long binarySearch(Account pBuscado) {
		return binarySearch(pBuscado, usersFile);
	}
	
	
	public void rewriteAccounts() {
		Account[] accounts = mezclaDirecta();
		eliminarArchivo(usersFile);
		usersFile = this.crearArchivo("usuarios.bin");
			for (Account actual: accounts) {
				try {
					this.write(actual, usersFile);
				} catch (ExistingUserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
	
	
	// Retorna la posicion donde se encuentra pBuscado en el archivo
	public long binarySearch(Account pBuscado, File pFile) {		
		if (getUserCount() == 0) return -1;
		
		int izq = 0;
		int drch = getUserCount(pFile) - 1;
		
		try {
			FileInputStream fileIS = new FileInputStream(pFile);
			ObjectInputStream oIS = new ObjectInputStream(fileIS);
			
			while (izq <= drch) {
				int medio = (izq + drch) / 2;
				// Mueve el puntero antes de leer el objeto guardado
				fileIS.getChannel().position(medio * ACCOUNT_BYTE_SIZE + OFFSET_INICIAL);
				Account accountActual = (Account) oIS.readObject();
				
				// Encuentra el indicado
				if (accountActual.compareTo(pBuscado) == 0) {
					long posEncontrado = fileIS.getChannel().position(); 
					oIS.close();
					fileIS.close();
					return (posEncontrado - OFFSET_INICIAL) / ACCOUNT_BYTE_SIZE;
				}
				
				// pBuscado es menor, se ignora la mayor mitad
				else if (pBuscado.compareTo(accountActual) < 0) {
					drch = medio - 1;
				}
				
				// pBuscado es mayor, se ignora la menor mitad
				else {
					izq = medio + 1;
				}	
				
			}
			// No esta en el archivo
			fileIS.close();
			oIS.close();
			
			return -1;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return -1;
	}
	
	
	public boolean fileIsEmpty(File pFile) {
		return usersFile.length() == 0;
	}
	
	
	// Hace un print de todos los Objects en usuarios.bin
	public void readAll() {
		readAll(usersFile);
	}
	
	public void readAll(File pFile) {
		if (fileIsEmpty(pFile)) return;
		
		FileInputStream FileIS;
		
		try {
		
			FileIS = new FileInputStream(pFile);
			ObjectInputStream OIS = new ObjectInputStream(FileIS);
			int counter = 0;
			
			while (counter < getUserCount(pFile)) {
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
 	

	public Account[] mezclaDirecta() {
		return mezclaDirecta(toArray());
	}
	
	
	public Account[] mezclaDirecta(Account[] pAccounts) {
		
		if (pAccounts.length > 1) {
			int cantidadIzq = pAccounts.length / 2;
			int cantidadDrch = pAccounts.length - cantidadIzq;
			Account[] auxiliar1 = new Account[cantidadIzq];
			Account[] auxiliar2 = new Account[cantidadDrch];
			
			for (int i = 0; i < cantidadIzq; i++) {
				auxiliar1[i] = pAccounts[i];
			}
			
			for (int i = cantidadIzq; i < cantidadIzq + cantidadDrch; i++) {
				auxiliar2[i - cantidadIzq] = pAccounts[i];
			}
			
			auxiliar1 = mezclaDirecta(auxiliar1);
			auxiliar2 = mezclaDirecta(auxiliar2);
			
			int posPAccounts = 0;
			int posAux1 = 0;
			int posAux2 = 0;
			
			while (auxiliar1.length != posAux1 && auxiliar2.length != posAux2) {
				if (auxiliar1[posAux1].compareTo(auxiliar2[posAux2]) < 0) {
					pAccounts[posPAccounts] = auxiliar1[posAux1];
					posPAccounts++;
					posAux1++;
				} else {
					pAccounts[posPAccounts] = auxiliar2[posAux2];
					posPAccounts++;
					posAux2++;
				}
			}
			
			while (auxiliar1.length != posAux1) {
				pAccounts[posPAccounts] = auxiliar1[posAux1];
				posPAccounts++;
				posAux1++;
			}
			
			while (auxiliar2.length != posAux2) {
				pAccounts[posPAccounts] = auxiliar2[posAux2];
				posPAccounts++;
				posAux2++;
			}
		}
		return pAccounts;
	}
	
	
	public void validarMail(Account pAccount) throws AddressException {
		InternetAddress correo = new InternetAddress(pAccount.getCorreo());
		correo.validate();
	}
	
	public Account getAccountByPos(long pPos) {
		if (pPos < 0) return null;
		Account account = null;
		try {
			FileInputStream fileIS = new FileInputStream(usersFile);
			ObjectInputStream oIS = new ObjectInputStream(fileIS);
			fileIS.getChannel().position(pPos * ACCOUNT_BYTE_SIZE + OFFSET_INICIAL);
			account = (Account) oIS.readObject();
			
			oIS.close();
			fileIS.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public Account[] toArray() {
		Account[] accounts = new Account[getUserCount()];
		
		FileInputStream fileIS;
		try {
			fileIS = new FileInputStream(usersFile);
			ObjectInputStream oIS = new ObjectInputStream(fileIS);
			
			for (int actual = 0; actual < getUserCount(); actual++) {
				fileIS.getChannel().position(actual * ACCOUNT_BYTE_SIZE + OFFSET_INICIAL);
				accounts[actual] = (Account) oIS.readObject();
			}
			fileIS.close();
			oIS.close();
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
		return accounts;
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
		return getUserCount(usersFile);
	}
	
	public int getUserCount(File pFile) {
		return (int) Math.floor(pFile.length() / ACCOUNT_BYTE_SIZE);
	}
	
}
