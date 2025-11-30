package database;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static String[] database;

    static {
        database = null;
        lock = false;
    }

    public static void populateDatabase(String filePath) {
        // Lendo o arquivo
        List<String> bdFileContents = new ArrayList<String>();
        try {
            bdFileContents = Files.readAllLines(Paths.get(filePath));
        } catch (IOException error) {
            System.out.println("Erro lendo a base: " + error);
        }

        // Armazenando na variável database
        database = new String[bdFileContents.size()];
        for(int i=0; i<bdFileContents.size(); i++) 
            database[i] = bdFileContents.get(i);
        return;
    }

    public static void cleanUp() {
        database = null;
    }
}
