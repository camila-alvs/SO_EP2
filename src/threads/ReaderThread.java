package threads;
public class ReaderThread extends Thread {
    private int id;
    private String[] words;
    private String value;

    public ReaderThread(int id, String[] words) {
        this.id = id;
        this.words = words;
    }

    public long getId() {
        return this.id;
    }
    public String getValue() {
        return value;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                int position = (int) Math.random() * words.length;
                this.value = words[position];
            }
            Thread.sleep(1);
        } catch (InterruptedException error) {
            System.out.println(error);
        }
    }
}