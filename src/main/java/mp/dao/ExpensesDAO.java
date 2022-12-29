package mp.dao;

import mp.tables.Expenses;

import java.util.List;

public interface ExpensesDAO {

    Expenses getExpensesForMonth(int year, String month);

    List<Expenses> getExpensesForYear(int year);

}
