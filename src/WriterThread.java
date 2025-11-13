public class WriterThread implements Runnable {
    private final int id;
    private final String[] words;

    public WriterThread(String[] words, int id) {
        this.id = id;
        this.words = words;
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