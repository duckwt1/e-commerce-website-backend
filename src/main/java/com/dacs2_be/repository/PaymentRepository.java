package com.dacs2_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dacs2_be.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
