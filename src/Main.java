import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
                threads[i] = new Thread(new WriterThread(words, i));
            } else {
                threads[i] = new Thread(new ReaderThread(words, i));
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
