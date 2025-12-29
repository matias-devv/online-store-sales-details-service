package com.onlinestore.sales_details_service.service;

import com.onlinestore.sales_details_service.dto.SaleDetailDTO;
import com.onlinestore.sales_details_service.model.SaleDetail;

import java.util.List;

public interface ISaleDetailService {

    public String createSaleDetail(SaleDetailDTO saleDetailDTO);

    public SaleDetailDTO findById(Long id);

    public List<SaleDetailDTO> findAllSaleDetails();

}
