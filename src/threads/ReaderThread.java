package threads;

import database.DataBase;
import manager.Manager;

public class ReaderThread extends Thread {
    private String value;

    public ReaderThread() {
        value = new String();
    }

    public String getValue() {
        return value;
    }

    public void run() {
        try {
            if(!Manager.acquireLock(this))
                wait(100000000);
            for(int i=0; i<100; i++) {
                int position = (int)(Math.random() * DataBase.database.length);
                value = DataBase.database[position];
            }
            sleep(1);
            Manager.releaseLock(this);
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}