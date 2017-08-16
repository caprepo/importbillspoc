package com.capgemini.importbills.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.importbills.model.Invoice;

@Repository
@Transactional
public interface ImportBillsDao extends JpaRepository<Invoice, Long> {

	@Query("SELECT p FROM Invoice p")
	public List<Invoice> findInvoices();
}
