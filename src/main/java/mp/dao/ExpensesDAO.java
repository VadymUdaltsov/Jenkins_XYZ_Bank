package mp.dao;

import mp.tables.Worker;

public interface ExpensesDAO {

    void getExpensesForMonth(int year, String month);

    void getExpensesForYear(int year);

}
