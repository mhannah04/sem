package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        app.printSalaries(employess);
    }

    @Test
    void displayEmployeesTestEmpty()
    {
        Employee emp = new Employee();
        app.displayEmployee(emp);
    }



    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        employess.add(null);
        app.printSalaries(employess);


    }



    @Test
    void displayEmployeeTestContainsNull()
    {
        Employee emp = null;
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestNormal()
    {

        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        emp.manager = "Max";
        emp.manager_last_name = "Hannah";

        app.displayEmployee(emp);
    }

    @Test
    void printSalariesTestNormal()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalaries(employees);
    }




    void getEmployeeTestEmpty()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        app.printSalaries(null);
    }
}