package mp;

import mp.dao.WorkerServiceMySQL;
import mp.tables.Worker;

import java.util.List;

public class Program {

    public static void main(String[] args) {
        WorkerServiceMySQL service = new WorkerServiceMySQL();
        final List<Worker> workers = service.getAllWorkers();
        System.out.println(workers);

        service.addWorker("Lebedynskaya", "Yaroslava");
        final List<Worker> updatedWorkersAfterAdded = service.getAllWorkers();
        System.out.println("After Added: ");
        for (Worker worker : updatedWorkersAfterAdded) {
            System.out.println(worker);
        }

        service.deleteWorker("Lebedynskaya", "Yaroslava");
        final List<Worker> updatedWorkersAfterDeleted = service.getAllWorkers();
        System.out.println("After Deleted: ");
        for (Worker worker : updatedWorkersAfterDeleted) {
            System.out.println(worker);
        }

        final Worker firstWorker = service.getWorkerByID(1);
        System.out.println("Get worker by ID");
        System.out.println(firstWorker);
    }
}
