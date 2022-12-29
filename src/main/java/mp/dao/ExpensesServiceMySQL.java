package mp.dao;

import mp.data.Month;
import mp.tables.Expenses;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import static mp.data.Const.*;

public class ExpensesServiceMySQL implements ExpensesDAO{

    @Override
    public void getExpensesForMonth(int year, String month) {

    }

    @Override
    public void getExpensesForYear(int year) {
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
                expenses.setMonth(Month.valueOf(resultSet.getString("month")));
            }
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
