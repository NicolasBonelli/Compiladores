package gramaticPackage;

import java.io.*;

public class FileASSEMCreator {
   
    public static void writeProgram(String file_name, String content) {
        File file = new File(file_name);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file_name);
            writer.write(content);
            writer.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    public static void eraseProgram(String file_name) {
    	File file = new File(file_name);
        
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("El archivo se ha borrado exitosamente.");
            } else {
                System.out.println("No se pudo borrar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }
    
}
