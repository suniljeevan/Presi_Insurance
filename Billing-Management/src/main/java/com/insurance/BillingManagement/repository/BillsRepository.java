package com.insurance.BillingManagement.repository;

import com.insurance.BillingManagement.entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepository extends JpaRepository<Bills, Long>
{
    Bills findByBillsId(long billsId);

}