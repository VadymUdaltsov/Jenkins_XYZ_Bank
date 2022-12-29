package testjob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestJob {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("John", 15),
                new Employee("Sam", 20),
                new Employee("Joe", 10),
                new Employee("", 50)
        ));

        Collections.sort(employees);
        System.out.println(employees);
    }
}
