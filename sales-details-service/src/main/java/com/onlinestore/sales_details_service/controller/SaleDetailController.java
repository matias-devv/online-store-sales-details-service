package com.onlinestore.sales_details_service.controller;

import com.onlinestore.sales_details_service.dto.SaleDetailDTO;
import com.onlinestore.sales_details_service.service.ISaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale-detail")
public class SaleDetailController {

    @Autowired
    private ISaleDetailService iSaleDetailService;

    @PostMapping("/create")
    public void createSaleDetail(@RequestBody SaleDetailDTO saleDetailDTO){
        iSaleDetailService.createSaleDetail(saleDetailDTO);
    }

    @GetMapping("/find/{id}")
    public SaleDetailDTO findById(Long id){
        return iSaleDetailService.findById(id);
    }

    @GetMapping("/find-all")
    public List<SaleDetailDTO> findAllSaleDetails(){
        return iSaleDetailService.findAllSaleDetails();
    }
}
