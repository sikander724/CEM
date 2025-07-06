package com.cem.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cem.DTO.ExpenseDTO;
import com.cem.Model.Expense;
import com.cem.Repositroy.ExpenseRepository;
import com.itextpdf.io.image.ImageDataFactory;
//import com.cem.Repository.ExpenseRepository;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	



	public Optional<Expense> getExpenseById(Long id) {
		return expenseRepository.findById(id);
	}

	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}

	// Method to store a new expense
	public Expense createExpense(Expense expense) {
		return expenseRepository.save(expense); // Saves the expense to the database
	}

	public byte[] generatePdfReport(List<Expense> expenses) {
	    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        
	     // Get the page size
	        float pageWidth = pdfDoc.getDefaultPageSize().getWidth();
	        float pageHeight = pdfDoc.getDefaultPageSize().getHeight();

	        String imagePath = "E:\\download\\pngtree-building-and-construction-logo-design-template-image_317780.jpg";
			// Load the image
	        Image img = new Image(ImageDataFactory.create(imagePath));

	        // Adjust the image width and height relative to the page size
	        float imgWidth = pageWidth * 0.2f; // Set width to 20% of the page width
	        float imgHeight = img.getImageHeight() * (imgWidth / img.getImageWidth()); // Maintain aspect ratio

	        // Position the image
	        float imgX = (pageWidth - imgWidth) / 2; // Center horizontally
	        float imgY = pageHeight - imgHeight +10; // 20 units from the top

	        // Set the image size and position
	        img.setWidth(imgWidth);
	        img.setHeight(imgHeight);
	        img.setFixedPosition(imgX, imgY);

	        // Add the image to the document
	        document.add(img);
	        // Add content to the PDF document
	        document.add(new Paragraph("Expense Report:"));
	        document.add(new Paragraph("Date: " + LocalDate.now()));

	        // Create a table with 8 columns: ID, Date, Category, Amount, Description, Paid Amount, Outstanding, Quantity
	        float[] columnWidths = {1, 2, 2, 2, 4, 4, 4, 4}; // Adjust column widths as needed
	        Table table = new Table(columnWidths);

	        // Add header row to the table
	        table.addCell(new Cell().add(new Paragraph("ID")));
	        table.addCell(new Cell().add(new Paragraph("Date")));
	        table.addCell(new Cell().add(new Paragraph("Category")));
	        table.addCell(new Cell().add(new Paragraph("Total Amount")));
	        table.addCell(new Cell().add(new Paragraph("Description")));
	        table.addCell(new Cell().add(new Paragraph("Paid Amount")));
	        table.addCell(new Cell().add(new Paragraph("Outstanding")));
	        table.addCell(new Cell().add(new Paragraph("Quantity")));

	        // Variables to hold the total amounts
	        BigDecimal finalTotalAmount = BigDecimal.ZERO;
	        BigDecimal totalOutstandingAmount = BigDecimal.ZERO;
	        BigDecimal finalTotalPaidAmount = BigDecimal.ZERO;
	        int finalTotalQuantity = 0;

	        // Add expense details to the table
	        for (Expense expense : expenses) {
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(expense.getId()))));  // ID
	            table.addCell(new Cell().add(new Paragraph(expense.getDate().toString())));     // Date
	            table.addCell(new Cell().add(new Paragraph(expense.getCategory())));           // Category
	            table.addCell(new Cell().add(new Paragraph(expense.gettotalAmount() != null ? expense.gettotalAmount().toString() : "0.00")));
	            table.addCell(new Cell().add(new Paragraph(expense.getDescription())));
	            table.addCell(new Cell().add(new Paragraph(expense.getPaidAmount() != null ? expense.getPaidAmount().toString() : "0.00")));
	            table.addCell(new Cell().add(new Paragraph(expense.getOutstanding() != null ? expense.getOutstanding().toString() : "0.00")));
	            table.addCell(new Cell().add(new Paragraph(expense.getQuantity() != null ? expense.getQuantity().toString() : "0")));

	            // Accumulate totals
	            finalTotalAmount = finalTotalAmount.add(expense.gettotalAmount() != null ? expense.gettotalAmount() : BigDecimal.ZERO);
	            totalOutstandingAmount = totalOutstandingAmount.add(expense.getOutstanding() != null ? expense.getOutstanding() : BigDecimal.ZERO);
	            finalTotalPaidAmount = finalTotalPaidAmount.add(expense.getPaidAmount() != null ? expense.getPaidAmount() : BigDecimal.ZERO);
	            finalTotalQuantity += expense.getQuantity() != null ? expense.getQuantity() : 0;
	        }

	        // Add the table to the document
	        document.add(table);

	        // Add total row to the document
	        document.add(new Paragraph("-------------------------------"));
	        document.add(new Paragraph("Final Total Amount: ₹" + String.format("%.2f", finalTotalAmount)));
	        document.add(new Paragraph("Final Outstanding Amount: ₹" + String.format("%.2f", totalOutstandingAmount)));
	        document.add(new Paragraph("Final Paid Amount: ₹" + String.format("%.2f", finalTotalPaidAmount)));
	        document.add(new Paragraph("Final Total Quantity: " + finalTotalQuantity));

	        // Close the document
	        document.close();

            // Return byte array of PDF content
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            // Handle IOException explicitly
            throw new RuntimeException("Error generating PDF report", e);
        }
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// Method to map from Entity to DTO
	private ExpenseDTO mapToDTO(Expense expense) {
		ExpenseDTO dto = new ExpenseDTO();
		dto.setStartDate(expense.getDate());
		dto.setCategory(expense.getCategory());
		dto.setInvoice(expense.getInvoice());
		dto.setIsPending(expense.isPending() ? "Yes" : "No"); // Assuming pending is a Boolean in Expense entity
		return dto;
	}
	public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
		// Ensure correct ordering of dates
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Start date cannot be after end date");
		}
		return expenseRepository.findExpensesBetweenOrOnDates(startDate, endDate);
	}
}
