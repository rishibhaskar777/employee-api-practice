package com.company.employee.exception;

// Thrown when no employee has the given id -> mapped to HTTP 404
public class EmployeeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(int id) {
        super("Employee not found with id " + id);
    }
}
