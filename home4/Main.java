package home4;
import java.util.*;
import java.io.*;

class MyThread extends Thread {
    private int threadScore;
    private String threadName;
    private Queue threadQueue;

    MyThread(Queue queue, String name) {
        threadScore = 0;
        threadName = name;
        threadQueue = queue;
        System.out.println("Creating thread: " +  threadName);
    }

    @Override
    public void run() {
        System.out.println("Begin thread: " + threadName);
        synchronized(threadQueue) {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {}
                try {
                    int num = (int) threadQueue.element();
                    threadQueue.remove();
                    threadScore += num;
                    //System.out.println("Delete: " + num);
                } catch (NoSuchElementException e) {}
            }
        }
	}

    public int getScore() {
        return threadScore;
    }
}

public class Main {
    public static void main(String[] args) {
        Queue<Integer> myQueue = new LinkedList<Integer>();
        int sum = 0;
        //MasterThread t0 = new MasterThread(myQueue, "t0");
        MyThread t1 = new MyThread(myQueue, "t1");
        MyThread t2 = new MyThread(myQueue, "t2");
        MyThread t3 = new MyThread(myQueue, "t3");

        t1.start();
        t2.start();
        t3.start();
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
                int min = 10;
                int max = 99;
                int diff = max - min;
                Random random = new Random();
                int res = random.nextInt(diff + 1) + min;
                //System.out.println("Add: "+ res);
                myQueue.add(res);
                sum += res;
        }

        System.out.println("First thread score: " + t1.getScore());
        System.out.println("Second thread score: " + t2.getScore());
        System.out.println("Third thread score: " + t3.getScore());
        System.out.println("Master thread sum: " + sum);
    }
}
