package week01_basics;

/**
 * Question3
 * Extends the Question2: creates 10 threads from each of the 2 classes in q2
 */

public class Question3 {

    public static void main(String[] args) {

        int threadsPerClass = 10;
        int numThreads = 2 * threadsPerClass;

        //positions 0..9: GreetingsRunnable, positions 10..19: SumRunnable
        Runnable[] tasks = new Runnable[numThreads];
        for (int i = 0; i < threadsPerClass; i++) {
            tasks[i] = new GreetingRunnable(i, "Hello #" + i);
            tasks[threadsPerClass + i] = new SumRunnable(threadsPerClass + i, (i + 1) * 10);
        }

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("In main: all " + numThreads + " threads done");
    }
}
