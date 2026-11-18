package com.company.employee.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.company.employee.model.Employee;

// Repository layer: all SQL (JDBC via JdbcTemplate)
@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbc;

    public EmployeeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Converts one row of the ResultSet into an Employee object
    private final RowMapper<Employee> mapper = (rs, rowNum) -> new Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getDouble("salary"));

    public List<Employee> findAll() {
        return jdbc.query("SELECT * FROM employee ORDER BY id", mapper);
    }

    public Optional<Employee> findById(int id) {
        return jdbc.query("SELECT * FROM employee WHERE id = ?", mapper, id)
                   .stream().findFirst();
    }

    public Employee save(Employee e) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO employee (name, department, salary) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getName());
            ps.setString(2, e.getDepartment());
            ps.setDouble(3, e.getSalary());
            return ps;
        }, keyHolder);
        e.setId(keyHolder.getKey().intValue());
        return e;
    }

    public int update(Employee e) {
        return jdbc.update("UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?",
                e.getName(), e.getDepartment(), e.getSalary(), e.getId());
    }

    public int deleteById(int id) {
        return jdbc.update("DELETE FROM employee WHERE id = ?", id);
    }
}
