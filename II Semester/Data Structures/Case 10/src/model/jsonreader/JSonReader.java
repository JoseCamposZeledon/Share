package model.jsonreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import model.sensor.Sensor;
import com.google.gson.*;

public class JSonReader implements JsonConstants {
	
	public JSonReader() {	
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Gson gson = new Gson();
        try (Reader reader = new FileReader(JSON_FILES_PATH + "//test.json")) {
        	
            // Convert JSON File to Java Object
        	Sensor sensor = gson.fromJson(reader, Sensor.class);
			// print staff 
            System.out.println(sensor);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
