package threads;

import database.DataBase;
import manager.Manager;

public class WriterThread extends Thread {
    private final String word;
    public final Object wait;
    public boolean paused;

    public WriterThread() {
        word = "MODIFICADO";
        wait = new Object();
        paused = false;
    }

    public void run() {
        try {
            synchronized(wait) {
                if(!Manager.acquireLock(this))
                    while(paused)
                        wait.wait();
                for (int i = 0; i < 100; i++) {
                    int position = (int) Math.random() * DataBase.database.length;
                    DataBase.database[position] = word;
                }
                Thread.sleep(1);
                Manager.releaseLock(this);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}