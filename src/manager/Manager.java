package manager;
import java.util.ArrayList;

import database.DataBase;
import threads.ReaderThread;
import threads.WriterThread;

public class Manager {
    private static Thread[] threadArray;
    private static boolean lock; // Se true base está bloqueada, se false base está livre
    private static ArrayList<Thread> lockQueue;
    private static Thread lockHolder;
    public static boolean stopExecution;

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
            threadsAux[i] = new WriterThread();
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

    /*
    * Dá o lock para uma thread acessar a base, dando preferência para writers na fila de espera
    */
    public static boolean acquireLock(Thread thread) {
        if(!lock) {
            lock = true;
            lockHolder = thread;
            return true;
        }
        else {
            if(thread instanceof ReaderThread) {
                for(int i=0; i<lockQueue.size(); i++) {
                    if(lockQueue.get(i) instanceof WriterThread) {
                        lockQueue.add(i, thread);
                        return false;
                    }
                }
            }
            lockQueue.add(thread);
            return false;
        }
    }

    /*
    Versão do acquireLock para a versão em que não há prioridade para writers na fila de espera
    public static boolean acquireLock(Thread thread) {
        if(!lock) {
            lock = true;
            lockHolder = thread;
            return true;
        }
        else {
            lockQueue.add(thread);
            return false;
        }
    }
    */

    /*
    * Libera o lock da thread que estava acessando a base e o passa para a próxima thread na fila de espera
    */
    public static void releaseLock(Thread thread) {
        if(!lockHolder.equals(thread))
            return;
        if(lockQueue.size()==0) {
            lock = false;
            lockHolder = null;
            return;
        }
        lockHolder = lockQueue.remove(0);
        if(lockHolder instanceof ReaderThread) {
            ReaderThread reader = (ReaderThread) lockHolder;
            synchronized(reader.wait) {
                reader.paused = false;
                reader.wait.notify();
            }
        }
        else {
            WriterThread writer = (WriterThread) lockHolder;
            synchronized(writer.wait) {
                writer.paused = false;
                writer.wait.notify();
            }
        }
    }

    /*
    * Inicializa a memória necessária
    */
    public static void setUp(String filePath, int readerAmount, int writerAmount) {
        DataBase.populateDatabase(filePath);
        createThreadArray(readerAmount, writerAmount);
        lock = false;
        lockQueue = new ArrayList<Thread>();
        lockHolder = null;
        stopExecution = false;
    }

    public static void run() {
        for(int i=0; i<threadArray.length; i++) {
            threadArray[i].start();
        }
        for(int i=0; i<threadArray.length; i++) {
            try {
                threadArray[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cleanUp();
    }

    /*
    * Termina a execução de threads vivas e limpa a memória
    */
    public static void cleanUp() {
        DataBase.cleanUp();
        for(int i=0; i<threadArray.length; i++) {
            if(threadArray[i].isAlive())
                threadArray[i].interrupt();
        }
    }
}
