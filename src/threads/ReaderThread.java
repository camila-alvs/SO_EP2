package threads;

import database.DataBase;

public class ReaderThread extends Thread {
    private String value;

    public ReaderThread() {
        value = new String();
    }

    public String getValue() {
        return value;
    }

    public void runReader() {
        try {
            for(int i=0; i<100; i++) {
                if(!DataBase.lock) {
                    int position = (int) Math.random()*DataBase.database.length;

                }
            }

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