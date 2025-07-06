package com.cem.DTO;

import java.time.LocalDate;

public class ExpenseDTO {
	private String category;
	private LocalDate startDate;
	private LocalDate endDate;
	private String invoice;
	private String isPending;

	// Getters and setters
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public String getIsPending() {
		return isPending;
	}

	public void setIsPending(String isPending) {
		this.isPending = isPending;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
}