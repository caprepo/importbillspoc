package com.capgemini.importbills.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component


@Entity
@Table(name = "invoice")
public class Invoice implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="invoice_id")
	private int invoiceId;

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	@Column(name="invoice_name")
	private String invoiceName;

	@Column(name="invoice_location")
	private String invoiceLocation;

	@Column(name="invoice_creation_date")
	private Timestamp invoiceCreationDate;

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceLocation() {
		return invoiceLocation;
	}

	public void setInvoiceLocation(String invoiceLocation) {
		this.invoiceLocation = invoiceLocation;
	}

	public Timestamp getInvoiceCreationDate() {
		return invoiceCreationDate;
	}

	public void setInvoiceCreationDate(Timestamp invoiceCreationDate) {
		this.invoiceCreationDate = invoiceCreationDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}