package com.cem.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cem.Model.Expense;
import com.cem.DTO.ExpenseDTO;
import com.cem.Model.*;
import com.cem.Service.ExpenseService;

import jakarta.security.auth.message.callback.PrivateKeyCallback.DigestRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // POST: Create a new expense
   @PostMapping("/create")
   public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
       return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    // GET: Retrieve an expense by ID
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
//        Optional<Expense> expense = expenseService.getExpenseById(id);
//        return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // GET: Retrieve all expenses
    @GetMapping("/getAll")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

 

    @PostMapping("/Reports")
    public ResponseEntity<List<Expense>> filterExpenses(@RequestBody Map<String, String> filterParams) {
        // Extract startDate and endDate from the request body
        LocalDate startDate = LocalDate.parse(filterParams.get("startDate"));
        LocalDate endDate = LocalDate.parse(filterParams.get("endDate"));

        // Call service to get filtered expenses
        List<Expense> filteredExpenses = expenseService.getExpensesByDateRange(startDate, endDate);

        // If no data is found, return HTTP 204 No Content
        if (filteredExpenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Return the filtered expenses
        return new ResponseEntity<>(filteredExpenses, HttpStatus.OK);
    }
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdfReport(@RequestBody Map<String, String> filterParams) {
        try {
            // Parse the start and end dates from the request body
            LocalDate startDate = LocalDate.parse(filterParams.get("startDate"));
            LocalDate endDate = LocalDate.parse(filterParams.get("endDate"));

            // Fetch filtered expenses based on the date range
            List<Expense> filteredExpenses = expenseService.getExpensesByDateRange(startDate, endDate);

            // Generate the PDF content as a byte array
            byte[] pdfContent = expenseService.generatePdfReport(filteredExpenses);

            // Set headers for the PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
            
            // Generate a dynamic filename with the start and end dates
            String filename = "ExpenseReport-" + startDate.toString() + "-to-" + endDate.toString() + ".pdf";
            
            // Set the content disposition with the dynamically generated filename
            headers.setContentDispositionFormData("attachment", filename);

            // Return the PDF content as a response entity
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an internal server error
            e.printStackTrace();  // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }