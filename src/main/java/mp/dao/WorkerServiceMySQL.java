package mp.dao;

import mp.tables.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static mp.data.Const.*;

public class WorkerServiceMySQL implements WorkerDAO {

    @Override
    public List<Worker> getAllWorkers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("SELECT * FROM worker");
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Worker> workers = new ArrayList<>();

            while (resultSet.next()) {
                addWorkers(resultSet, workers);
            }
            return workers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Worker getWorkerByID(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("SELECT * FROM worker WHERE worker_ID = ?");
            preparedStatement.setInt(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            Worker worker = new Worker();
            while (resultSet.next()) {
                worker.setWorker_ID(resultSet.getInt("worker_ID"));
                worker.setSurname(resultSet.getString("surname"));
                worker.setName(resultSet.getString("name"));
            }

            return worker;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addWorker(String surname, String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            String insert = "INSERT INTO worker(surname, name) VALUES (?, ?)";

            preparedStatement = CONNECTION.prepareStatement(insert);
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteWorker(String surname, String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            String delete = "DELETE FROM worker WHERE surname = ? AND name = ?";

            preparedStatement = CONNECTION.prepareStatement(delete);
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void addWorkers(ResultSet resultSet, List<Worker> workers) {
        Worker worker = new Worker();
        if (workers.size() == 0) {
            worker.setWorker_ID(1);
        } else {
            worker.setWorker_ID(workers.get(workers.size() - 1).getWorker_ID() + 1);
        }
        try {
            worker.setSurname(resultSet.getString("surname"));
            worker.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        workers.add(worker);
    }
}
