package com.cem.Repositroy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cem.Model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByCategoryAndDateBetweenAndInvoiceAndIsPending(String category, LocalDate startDate,
			LocalDate endDate, String invoice, Boolean isPending);

	List<Expense> findByCategoryAndDateBetween(String category, LocalDate startDate, LocalDate endDate);

	List<Expense> findByCategory(String category);

	@Query("SELECT e FROM Expense e WHERE e.date >= :startDate AND e.date <= :endDate")
	List<Expense> findExpensesBetweenOrOnDates(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	List<Expense> findByInvoice(String invoice);

	List<Expense> findByIsPending(Boolean isPending);

	List<Expense> findAll();
	// List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
