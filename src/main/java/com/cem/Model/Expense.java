package com.cem.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "category")
	private String category;

	@Column(name = "description")
	private String description;

	@Column(name = "totalamount")
	private BigDecimal totalAmount;

	@Column(name = "is_pending")
	private boolean isPending;

	@Column(name = "paid_amount")
	private BigDecimal paidAmount;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "expense_type")
	private String expenseType;

	@Column(name = "vendor_name")
	private String vendorName;

	@Column(name = "invoice")
	private String invoice;

	@Column(name = "photo")
	private String photo;
	@Column(name = "outstanding")
	private BigDecimal outstanding;
	
	// Default constructor
	public Expense() {
	}

	// Parameterized constructor
	public Expense(Long id, LocalDate date, String category, String description, BigDecimal totalAmount,
			boolean isPending, BigDecimal paidAmount, Integer quantity, Long projectId, String expenseType,
			String vendorName, String invoice, String photo,BigDecimal outstanding) {
		this.id = id;
		this.date = date;
		this.category = category;
		this.description = description;
		this.totalAmount = totalAmount;
		this.isPending = isPending;
		this.paidAmount = paidAmount;
		this.quantity = quantity;
		this.projectId = projectId;
		this.expenseType = expenseType;
		this.vendorName = vendorName;
		this.invoice = invoice;
		this.photo = photo;
		this.outstanding=outstanding;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal gettotalAmount() {
		return totalAmount;
	}

	public void settotalAmount(BigDecimal Amount) {
		this.totalAmount = Amount;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

public BigDecimal getOutstanding() {
	return outstanding;
}

public void setOutstanding(BigDecimal outstanding) {
	this.outstanding = outstanding;
}
}
