package week01_basics;

/**
 * Question4
 * Set isolateThread to a thread id to eun only that thread or to -1
 * 1 thread alone: Its multiplies appear in order (1 to 20), because its thread runs its own loop sequentially
 * All threads together: the lines of different threads are mixed and the order is different on every run
 */

public class Question4 {

    public static void main(String[] args) {

        int numThreads = 10;
        int isolateThread = -1;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new MultiplesRunnable(i));

            if (isolateThread == -1 || isolateThread == i) {
                threads[i].start();
            }
        }

        // wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MultiplesRunnable implements Runnable {

    private int myId;
    private int number;

    public MultiplesRunnable(int id) {
        myId = id;
        number = id + 1;
    }

    @Override
    public void run() {
        for (int k = 1; k <= 20; k++) {
            System.out.println("[thread " + myId + "] " + k + " * " + number + " = " + (k * number));
        }
    }

}