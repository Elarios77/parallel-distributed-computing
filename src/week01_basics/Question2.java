package week01_basics;

/**
 * Question2
 * main() creates 2 threads, each one running on a different class. The 2 classes
 * have different constructors and run().
 * Both of them implement the Runnable and the polymorphism is that the:
 * new Thread(...) accepts 2 different threads of Runnable type.
 */

public class Question2 {

    public static void main(String[] args) {

        //2 tasks: one object of each class
        Runnable[] tasks = new Runnable[2];
        tasks[0] = new GreetingRunnable(0, "Hello from the greeting thread");
        tasks[1] = new SumRunnable(1, 10);

        //create and start threads
        Thread[] threads = new Thread[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        //wait to finish
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("In main: threads all done");
    }
}

//First class prints a message
class GreetingRunnable implements Runnable {
    private int myId;
    private String message;

    public GreetingRunnable(int id, String msg) {
        myId = id;
        message = msg;
    }

    @Override
    public void run() {
        System.out.println("[GreetingRunnable " + myId + " ] " + message);
    }
}

//calculates the sum 1 + ... + n
class SumRunnable implements Runnable {
    private int myId;
    private int n;

    public SumRunnable(int id, int n) {
        myId = id;
        this.n = n;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        System.out.println("[SumRunnable " + myId + " ] sum(1.." + n + ") = " + sum);
    }
}
