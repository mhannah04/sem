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

    void testGetEmployeeCurrent()
    {
        Employee emp = app.getEmployee(10734);
        assertEquals(emp.emp_no, 10734);
        assertEquals(emp.first_name, "Sachar");
        assertEquals(emp.last_name, "Erdi");
        assertEquals(emp.title, "Engineer");
        assertEquals(emp.salary, 60732);
        assertEquals(emp.manager, "Oscar");
        assertEquals(emp.manager_last_name, "Ghazalie");
    }
}
