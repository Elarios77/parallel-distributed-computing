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

    static final int NUM_THREADS = 10;
    static final String DEFAULT_VARIANT = "array";

    static Runnable task(int id) {
        return () -> {
            try {
                Thread.sleep((NUM_THREADS - id) * 50L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread " + id + " finished");
        };
    }

    // Correct approach: start all threads first, then join all of them
    static void withArray() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(task(i));
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    //This is when no references kept
    static void noJoin() {
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(task(i)).start();
        }
    }

    //b)
    static void joinLastOnly() throws InterruptedException {
        Thread last = null;
        for (int i = 0; i < NUM_THREADS; i++) {
            last = new Thread(task(i));
            last.start();
        }
        last.join();
    }

    public static void main(String[] args){

    }
}