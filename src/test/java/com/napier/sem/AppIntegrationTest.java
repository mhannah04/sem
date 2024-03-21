package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AppIntegrationTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 5000);

    }

    @Test
    void testGetEmployee()
    {
        Employee emp = app.getEmployee(255530);
        assertEquals(emp.emp_no, 255530);
        assertEquals(emp.first_name, "Ronghao");
        assertEquals(emp.last_name, "Garigliano");
    }

    @Test
    void testGetEmployeeCurrent()
    {
        Employee emp = app.getEmployeeCurrent(10734);
        System.out.println(emp.emp_no);
        assertEquals(emp.emp_no, 10734);
        assertEquals(emp.first_name, "Sachar");
        assertEquals(emp.last_name, "Erdi");
        assertEquals(emp.title, "Engineer");
        assertEquals(emp.salary, 60732);
        assertEquals(emp.manager, "Oscar");
        assertEquals(emp.manager_last_name, "Ghazalie");
    }

    @Test
    void testAddEmployee()
    {
        Employee emp = new Employee();
        emp.emp_no = 500000;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        app.addEmployee(emp);
        emp = app.getEmployee(500000);
        assertEquals(emp.emp_no, 500000);
        assertEquals(emp.first_name, "Kevin");
        assertEquals(emp.last_name, "Chalmers");
    }
}
