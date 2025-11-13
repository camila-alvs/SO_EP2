public class ReaderThread implements Runnable {
    private final int id;
    private final String[] words;

    private String value;

    public ReaderThread(int id, String[] words) {
        this.id = id;
        this.words = words;
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