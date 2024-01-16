package com.uxm.blockchain.domain.purchase.repository;

import com.uxm.blockchain.domain.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
