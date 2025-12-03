package pi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class MasterError {
    public double doRun(int totalCount, int numWorkers) throws InterruptedException, ExecutionException
    {

        // Create a collection of tasks
        List<Callable<Long>> tasks = new ArrayList<Callable<Long>>(); //liste de tâches callable
        for (int i = 0; i < numWorkers; ++i)
        {
            tasks.add(new Worker(totalCount));
        }

        // Run them and receive a collection of Futures
        ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
        List<Future<Long>> results = exec.invokeAll(tasks);
        long total = 0;

        // Assemble the results.
        for (Future<Long> f : results)
        {
            // Call to get() is an implicit barrier.  This will block
            // until result from corresponding worker is ready.
            total += f.get(); //récupère résultats au fur et à mesure et additionne
        }
        double pi = 4.0 * total / totalCount / numWorkers;

        exec.shutdown();
        return pi;
    }
}