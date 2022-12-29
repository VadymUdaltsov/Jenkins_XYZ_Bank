package mp.dao;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import mp.data.Month;
import mp.tables.Expenses;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static mp.data.Const.*;

public class ExpensesServiceMySQL implements ExpensesDAO {

    @Override
    public Expenses getExpensesForMonth(int year, String month) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("SELECT * FROM monthly_expenses WHERE year = ? AND month = ?");
            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, month);
            final ResultSet resultSet = preparedStatement.executeQuery();

            Expenses expenses = new Expenses();

            while (resultSet.next()) {
                initExpenses(resultSet, expenses);
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

            List<Expenses> fullYear = new ArrayList<>();

            while (resultSet.next()) {
                Expenses expenses = new Expenses();
                initExpenses(resultSet, expenses);
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

    @Override
    public void addExpensesMonth(Expenses expenses) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = CONNECTION.prepareStatement("INSERT INTO monthly_expenses(year, month, food, " +
                    "accountant, number_account, internet, house, cat) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            final Field[] fields = expenses.getClass().getDeclaredFields();

            final PreparedStatement getMonth = CONNECTION.prepareStatement("SELECT * FROM monthly_expenses WHERE year = ?");
            getMonth.setInt(1, 2022);
            final ResultSet resultSet = getMonth.executeQuery();
            final com.mysql.cj.result.Field[] resultSetFields = ((ResultSetImpl) resultSet).getMetadata().getFields();

            List<String> sqlFieldType = new ArrayList<>();
            for (com.mysql.cj.result.Field resultSetField : resultSetFields) {
                sqlFieldType.add(resultSetField.getMysqlType().getName());
            }

            int paramIndex = 1;
            int listIndex = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                final String type = sqlFieldType.get(listIndex);
                if (type.equals("INT")) {
                    preparedStatement.setInt(paramIndex, field.getInt(expenses));
                } else {
                    final String month = StringUtils.capitalize(field.get(expenses).toString().toLowerCase());
                    preparedStatement.setString(paramIndex, month);
                }
                paramIndex++;
                listIndex++;
            }
            preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
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
    public void deleteExpensesMonth(int year, String month) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            String delete = "DELETE FROM monthly_expenses WHERE year = ? AND month = ?";
            preparedStatement = CONNECTION.prepareStatement(delete);

            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, month);
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


    private void initExpenses(ResultSet resultSet, Expenses expenses) throws SQLException {
        expenses.setYear(resultSet.getInt("year"));
        expenses.setMonth(Month.valueOf(resultSet.getString("month").toUpperCase()));
        expenses.setFood(resultSet.getInt("food"));
        expenses.setAccountant(resultSet.getInt("accountant"));
        expenses.setPhone(resultSet.getInt("number_account"));
        expenses.setInternet(resultSet.getInt("internet"));
        expenses.setHouse(resultSet.getInt("house"));
        expenses.setCat(resultSet.getInt("cat"));
    }
}
