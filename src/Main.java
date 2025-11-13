import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Runnable;

public class Main {

    /*
     * Cria o array de threads seguindo a proporção definida de readers e writers
     */
    public Runnable[] createThreadArray(int readerAmout, int writerAmount) {
        // Como o array deve ter exatamente 100 posições, readerAmount + writerAmount deve ser igual a 100
        if(readerAmout+writerAmount!=100) {
            System.out.println("O arranjo de threads deve ter exatamente 100 objetos. Verifique as quantidade de writers e readers.");
            return null;
        }

        // Criando um array de 100 threads na proporção definida
        Runnable[] threads = new Runnable[100];
        for(int i=0; i<writerAmount; i++)
            threads[i] = new WriterThread(i, new String[100]);
        for(int i=writerAmount; i<writerAmount+readerAmout; i++)
            threads[i] = new ReaderThread(i, new String[100]);

        // Embaralhando o array
        // Coloca em posições aleatórias. Se a posição já estiver ocupada, tenta a próxima até encontrar uma livre
        Runnable [] shuffledThreads = new Runnable[100];
        for(int i=0; i<threads.length; i++) {
            int position = (int) (Math.random()*100);
            if(shuffledThreads[position]==null)
                shuffledThreads[position] = threads[i];
            else {
                for(int j=1; j<100; j++) {
                    int aux = (position + j) % 100;
                    if(shuffledThreads[aux]==null) {
                        shuffledThreads[aux] = threads[i];
                        break;
                    }
                }
            }
        }
        return shuffledThreads;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Starting...");
        System.out.print("Selecione a proporção de Readers (0-100):  ");
        int readerAmount = scanner.nextInt();
        System.out.print("Selecione a proporção de Writers (0-100):  ");
        int writerAmount = scanner.nextInt();

        Runnable[] threadArray = new Main().createThreadArray(readerAmount, writerAmount);

        scanner.close();


        int size = 0;
        List<String> bdFileContents = new ArrayList<String>();
        String filePath = "./bd.txt";
        Thread[] threads = new Thread[100];

        try {
            bdFileContents = Files.readAllLines(Paths.get(filePath));
            size = bdFileContents.size();
        } catch (IOException error) {
            System.out.println(error);
        }

        String[] words = new String[size];

        for (int i = 0; i < size; i++) {
            words[i] = bdFileContents.get(i);
        }

        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(new WriterThread(i, words));
            } else {
                threads[i] = new Thread(new ReaderThread(i, words));
            }
        }

        System.out.println("Defined the array!");

        for (Thread t : threads)
        t.start();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("All threads finished.");

        for (int i = 0; i < size; i++) {
            System.out.println(words[i]);
        }
    }
}
