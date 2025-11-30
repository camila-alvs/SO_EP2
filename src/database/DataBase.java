package database;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static String[] database;

    static {
        database = null;
    }

    public static void populateDatabase(String filePath) {
        List<String> bdFileContents = new ArrayList<String>();
        String line;
        try{
            // Acessando o arquivo
            FileReader arquivo = new FileReader(filePath);
            BufferedReader leitor = new BufferedReader(arquivo);
            
            // Lendo o arquivo
            while((line=leitor.readLine())!=null) {
                bdFileContents.add(line);
            }
            leitor.close();
        } catch(IOException error) {
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
