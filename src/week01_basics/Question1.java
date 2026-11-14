package week01_basics;

/**
 * Question1 -> read instructions
 * Answer: join() needs a reference to the thread we want to wait for. Without an array (or list)
 * the references are lost and we can not hold thread's references. After the first loop, main does not
 * have a way to call join(), so it will execute the rest code. Main thread finishes of firs
 * but the program does not stop until all the non-daemon threads are finished. If join() executes right
 * after .start(), parallelism is lost (this is sequential).
 * b) Also a single variable overwritten in the loop: only the last thread is joined, which
 * does not guarantee that the other threads have finished.  *
 */

public class Question1 {

    public static void main(String[] args) {
        int numThreads = 10;
        int variant = 1;

        long start = System.currentTimeMillis(); //Returns the current time in milliseconds

        if (variant == 1) {
            /* 1) with array: start all threads, then join all of them */

            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                threads[i] = new Thread(new SleepRunnable(i, numThreads));
                threads[i].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (variant == 2) {
            /* 2) no array so we loose reference and we can't call joi() */
            for (int i = 0; i < numThreads; i++) {
                Thread t = new Thread(new SleepRunnable(i, numThreads));
                t.start();
            }
        } else if (variant == 3) {
            /* 3) join() right after start(): each thread finishes before the next starts */
            for (int i = 0; i < numThreads; i++) {
                Thread t = new Thread(new SleepRunnable(i, numThreads));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            /* 4) one variable that is overwritten: only the last thread is joined */
            Thread t = null;

            for (int i = 0; i < numThreads; i++) {
                t = new Thread(new SleepRunnable(i, numThreads));
                t.start();
            }

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("In main: threads all done (" + elapsed + " ms)");
    }
}

/**
 * The code each thread executes
 * Thread[i] sleeps (totalThreads - i ) * 50, so thread 0 finishes last and the last finishes first
 */

class SleepRunnable implements Runnable {

    private final int myId;
    private final int totalThreads;

    public SleepRunnable(int id, int total) {
        myId = id;
        totalThreads = total;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((totalThreads - myId) * 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + myId + " finished");
    }
}