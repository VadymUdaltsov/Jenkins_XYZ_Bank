package mp;

import mp.dao.ExpensesServiceMySQL;
import mp.dao.WorkerServiceMySQL;
import mp.tables.Expenses;
import mp.tables.Worker;

import java.util.List;

public class Program {

    public static void main(String[] args) {
        WorkerServiceMySQL workerServiceMySQL = new WorkerServiceMySQL();
        final List<Worker> workers = workerServiceMySQL.getAllWorkers();
        System.out.println(workers);

        workerServiceMySQL.addWorker("Lebedynskaya", "Yaroslava");
        final List<Worker> updatedWorkersAfterAdded = workerServiceMySQL.getAllWorkers();
        System.out.println("After Added: ");
        for (Worker worker : updatedWorkersAfterAdded) {
            System.out.println(worker);
        }

        workerServiceMySQL.deleteWorker("Lebedynskaya", "Yaroslava");
        final List<Worker> updatedWorkersAfterDeleted = workerServiceMySQL.getAllWorkers();
        System.out.println("After Deleted: ");
        for (Worker worker : updatedWorkersAfterDeleted) {
            System.out.println(worker);
        }

        final Worker firstWorker = workerServiceMySQL.getWorkerByID(1);
        System.out.println("Get worker by ID");
        System.out.println(firstWorker);

        ExpensesServiceMySQL expensesServiceMySQL = new ExpensesServiceMySQL();
        final List<Expenses> expensesForYear = expensesServiceMySQL.getExpensesForYear(2022);
        System.out.println(expensesForYear);

        final Expenses december = expensesServiceMySQL.getExpensesForMonth(2022, "December");
        System.out.println(december);
    }
}
