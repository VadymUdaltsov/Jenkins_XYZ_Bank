package mp.dao;

import mp.data.Month;
import mp.tables.Expenses;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static mp.data.Const.*;

public class ExpensesServiceMySQL implements ExpensesDAO{

    @Override
    public Expenses getExpensesForMonth(int year, String month) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("SELECT * FROM monthly_expenses WHERE year = ?");
            preparedStatement.setInt(1, year);
            final ResultSet resultSet = preparedStatement.executeQuery();
            Expenses expenses = new Expenses();
            while (resultSet.next()) {
                expenses.setYear(resultSet.getInt("year"));
                expenses.setMonth(Month.valueOf(resultSet.getString("month").toUpperCase()));
                expenses.setFood(resultSet.getInt("food"));
                expenses.setAccountant(resultSet.getInt("accountant"));
                expenses.setPhone(resultSet.getInt("number_account"));
                expenses.setInternet(resultSet.getInt("internet"));
                expenses.setHouse(resultSet.getInt("house"));
                expenses.setCat(resultSet.getInt("cat"));
            }

            return expenses;
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
    public List<Expenses> getExpensesForYear(int year) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("SELECT * FROM monthly_expenses WHERE year = ?");
            preparedStatement.setInt(1, year);
            final ResultSet resultSet = preparedStatement.executeQuery();
            Expenses expenses = new Expenses();
            List<Expenses> fullYear = new ArrayList<>();
            while (resultSet.next()) {
                expenses.setYear(resultSet.getInt("year"));
                expenses.setMonth(Month.valueOf(resultSet.getString("month").toUpperCase()));
                expenses.setFood(resultSet.getInt("food"));
                expenses.setAccountant(resultSet.getInt("accountant"));
                expenses.setPhone(resultSet.getInt("number_account"));
                expenses.setInternet(resultSet.getInt("internet"));
                expenses.setHouse(resultSet.getInt("house"));
                expenses.setCat(resultSet.getInt("cat"));

                fullYear.add(expenses);
            }

            return fullYear;
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
}
