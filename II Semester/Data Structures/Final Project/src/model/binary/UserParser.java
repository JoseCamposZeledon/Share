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

import model.user.User;

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
	public void write(User pUser) {
		
		// El usuario ya esta en el archivo
		
		if (binarySearch(pUser)) return;
		
		// Se escribe el nuevo usuario en el archivo y se ordena 
		try {
			
			FileOutputStream fileOS = new FileOutputStream(usersFile, true);
			ObjectOutputStream userOS = new ObjectOutputStream(fileOS);
			
			userOS.writeObject((Object) pUser);
			
			userOS.close();
			fileOS.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO - Busqueda Binaria
	public boolean binarySearch(User pUser) {
		try {
			RandomAccessFile raf = new RandomAccessFile(usersFile, "r");
			
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		UserParser test = UserParser.getInstancia();
		
		test.write(user3);
	}
	
}
