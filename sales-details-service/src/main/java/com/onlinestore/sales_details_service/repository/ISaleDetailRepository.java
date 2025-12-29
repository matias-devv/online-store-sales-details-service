package com.onlinestore.sales_details_service.repository;

import com.onlinestore.sales_details_service.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
