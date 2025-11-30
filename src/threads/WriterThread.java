package threads;

import database.DataBase;
import manager.Manager;

public class WriterThread extends Thread {
    private final String word;

    public WriterThread() {
        word = "MODIFICADO";
    }

    public void run() {
        try {
            if(!Manager.acquireLock(this))
                wait(100000000);
            for (int i = 0; i < 100; i++) {
                int position = (int) Math.random() * DataBase.database.length;
                DataBase.database[position] = word;
            }
            Thread.sleep(1);
            Manager.releaseLock(this);
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}