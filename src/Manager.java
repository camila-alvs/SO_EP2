import database.DataBase;
import threads.ReaderThread;
import threads.WriterThread;

public class Manager {
    static Thread[] threadArray;

    /*
     * Cria o arranjo de threads seguindo a proporção definida de readers e writers
     */
    static void createThreadArray(int readerAmount, int writerAmount) {
        // Verifica se readerAmount+writerAmount == 100 (o arranjo deve ter exatamente 100 posições)
        if(readerAmount+writerAmount!=100) {
            System.out.println("Verifique as quantidade de writers e readers. O arranjo de threads deve ter exatamente 100 objetos.");
            return;
        }
        // Criando o arranjo
        Thread[] threadsAux = new Thread[readerAmount+writerAmount];
        for(int i=0; i<readerAmount; i++)
            threadsAux[i] = new ReaderThread();
        for(int i=readerAmount; i<readerAmount+writerAmount; i++)
            threadsAux[i] = new WriterThread(i, new String[100]);
        // Embaralhando (coloca em posições aleatórias. Se a posição já estiver ocupada, tenta a próxima até encontrar uma livre
        threadArray = new Thread[readerAmount+writerAmount];
        for(int i=0; i<threadsAux.length; i++) {
            int position = (int) (Math.random()*100);
            if(threadArray[position]==null)
                threadArray[position] = threadsAux[i];
            else {
                for(int j=1; j<100; j++) {
                    int newPos = (position + j) % 100;
                    if(threadArray[newPos]==null) {
                        threadArray[newPos] = threadsAux[i];
                        break;
                    }
                }
            }
        }
        return;
    }

    // Inicializa a memória necessária
    public static void setUp(String filePath, int readerAmount, int writerAmount) {
        DataBase.populateDatabase(filePath);
        createThreadArray(readerAmount, writerAmount);
    }
    
    // Roda as threads
    public static void runWithRoles() {
        return;
    }
    public static void runGeneric() {
        return;
    }

    public static void displayTimes() {
        return;
    }

    public static boolean isAlive() {
        return false;
    }

    // Termina a execução de threads vivas e limpa a memória
    // !!!! TERMINAR !!!!
    public static void cleanUp() {
        DataBase.cleanUp();
        for(int i=0; i<threadArray.length; i++) {
            if(threadArray[i].isAlive())
                threadArray[i].interrupt();
        }
    }
    // !!!! TERMINAR !!!!

    /*
       
    





     */
}
