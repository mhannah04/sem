package com.napier.sem;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;

import static java.lang.System.in;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get Employee
        Employee emp = a.getEmployeeCurrent(10200);
        // Display results

        // a.displayEmployee(emp);
        //Employee emp = a.getAllSalaries("Engineer");
        //a.displayEmployeeSalary(emp);
        // Disconnect from database

        ArrayList<Employee> employees = a.getEmployeeByTitle("title");
        System.out.println(employees.size());
        a.displayEmployeeSalary(employees);
        a.disconnect();


    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(5000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public ArrayList<Employee> getEmployeeByTitle(String Title)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = " SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary"
            +" FROM employees, salaries, titles"
            +" WHERE employees.emp_no = salaries.emp_no"
            +" AND employees.emp_no = titles.emp_no"
            +" AND salaries.to_date = '9999-01-01'"
            +" AND titles.to_date = '9999-01-01'"
            //+" AND titles.title = '" + Title + "'"
                    + " AND titles.title = 'Engineer'"
            +" ORDER BY employees.emp_no ASC";




            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> employees = new ArrayList<Employee>();
            // Return new employee if valid.
            // Check one is returned
            while (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.salary = rset.getInt("salary");
                employees.add(emp);
            }
            return employees;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }


    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                Employee emp = new Employee();
                //emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }



    public Employee getEmployeeCurrent(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT e.first_name AS employee_first_name, e.last_name AS employee_last_name, t.title AS employee_title, d.dept_name AS department_name, s.salary AS employee_salary, m.first_name AS manager_first_name,m.last_name AS manager_last_name "
                            +"FROM employees e "
                            +"INNER JOIN dept_emp de ON e.emp_no = de.emp_no "
                            +"INNER JOIN departments d ON de.dept_no = d.dept_no "
                            +"INNER JOIN dept_manager dm ON de.dept_no = dm.dept_no "
                            +"INNER JOIN employees m ON dm.emp_no = m.emp_no "
                            +"INNER JOIN titles t ON e.emp_no = t.emp_no "
                            +"INNER JOIN salaries s ON e.emp_no = s.emp_no "
                            +"WHERE e.emp_no = " +ID
                            +" AND de.to_date = '9999-01-01' "
                            +"AND dm.to_date = '9999-01-01' "
                            +"AND t.to_date = '9999-01-01' "
                            +"AND s.to_date = '9999-01-01' ";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.first_name = rset.getString("employee_first_name");
                emp.last_name = rset.getString("employee_last_name");
                emp.title = rset.getString("employee_title");
                emp.dept_name = rset.getString("department_name");
                emp.salary = rset.getInt("employee_salary");
                emp.manager = rset.getString("manager_first_name");
                emp.manager_last_name = rset.getString("manager_last_name");
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }
    


    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager First Name: " + emp.manager + "\n"
                            + "Manager Last Name: " + emp.manager_last_name);
        } else {
            System.out.println("employee object null");
        }
    }

    public void displayEmployeeSalary(ArrayList<Employee> employees)
    {
        for(Employee emp : employees){
            if (emp != null)
            {
                System.out.println(
                        emp.emp_no + " "
                                + emp.first_name + " "
                                + emp.last_name + "\n"
                                + "Salary:" + emp.salary + "\n");
            }
            else {
                System.out.println("employee object null");
            }
        }
    }
}