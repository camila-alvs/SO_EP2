package threads;

import database.DataBase;
import manager.Manager;

public class ReaderThread extends Thread {
    private String value;
    public final Object wait;
    public boolean paused;

    public ReaderThread() {
        value = new String();
        wait = new Object();
        paused = false;
    }

    public String getValue() {
        return value;
    }

    public void run() {
        try {
            synchronized(wait) {
                if(!Manager.acquireLock(this))
                    while(paused)
                        wait.wait();
                for(int i=0; i<100; i++) {
                    int position = (int)(Math.random() * DataBase.database.length);
                    value = DataBase.database[position];
                }
                sleep(1);
                Manager.releaseLock(this);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}