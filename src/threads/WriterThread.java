package threads;
public class WriterThread extends Thread {
    private int id;
    private final String[] words;

    public WriterThread(int id, String[] words) {
        this.id = id;
        this.words = words;
    }

    public long getId() {
        return this.id;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                int position = (int) Math.random() * words.length;
                words[position] = "MODIFICADO";
            }
            Thread.sleep(1);
        } catch (InterruptedException error) {
            System.out.println(error);
        }
    }
}