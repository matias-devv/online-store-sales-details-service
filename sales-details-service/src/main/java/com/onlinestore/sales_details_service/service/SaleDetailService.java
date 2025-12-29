package com.onlinestore.sales_details_service.service;

import com.onlinestore.sales_details_service.dto.ProductDTO;
import com.onlinestore.sales_details_service.dto.SaleDetailDTO;
import com.onlinestore.sales_details_service.dto.ShoppingCartDTO;
import com.onlinestore.sales_details_service.feign.IProductAPI;
import com.onlinestore.sales_details_service.feign.IShopping_cartAPI;
import com.onlinestore.sales_details_service.model.SaleDetail;
import com.onlinestore.sales_details_service.repository.ISaleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleDetailService implements ISaleDetailService{

    @Autowired
    private ISaleDetailRepository iSaleDetailRepository;

    @Autowired
    private IShopping_cartAPI iShoppingCartAPI;

    @Autowired
    private IProductAPI iProductAPI;

    @Override
    public String createSaleDetail(SaleDetailDTO saleDetailDTO) {

        SaleDetail saleDetail = this.convertDTOtoSaleDetailEntity(saleDetailDTO);
        iSaleDetailRepository.save(saleDetail);

        return "The detail of the sale was successfully created";
    }

   //implement circuit breaker
    private SaleDetail convertDTOtoSaleDetailEntity(SaleDetailDTO saleDetailDTO) {
        SaleDetail saleDetail = new SaleDetail();

        saleDetail.setId_user(saleDetailDTO.getId_user());
        saleDetail.setId_sale(saleDetailDTO.getId_sale());
        saleDetail.setTotal_price(saleDetailDTO.getTotal_price());
        saleDetail.setId_shopping_cart(saleDetailDTO.getId_shopping_cart());

        //I'm looking for the shopping cart by ID
        //Once I have the list of carts ->  I set it to "saleDetail"
        ShoppingCartDTO shoppingCartDTO = iShoppingCartAPI.findShoppingCartById( saleDetailDTO.getId_shopping_cart() );
        List<ProductDTO> productList = iProductAPI.findProductsByCode( shoppingCartDTO.getProduct_codes() );
        saleDetail.setProducts_to_take(productList);

        return saleDetail;
    }

    @Override
    public SaleDetailDTO findById(Long id) {
        SaleDetail saleDetail = iSaleDetailRepository.findById(id).orElse(null);
        return this.convertSaleDetailToDTO(saleDetail);
    }

    @Override
    public List<SaleDetailDTO> findAllSaleDetails() {
        List<SaleDetail> saleDetails = iSaleDetailRepository.findAll();
        List<SaleDetailDTO> saleDetailDTOs = new ArrayList<>();

        for(SaleDetail saleDetail : saleDetails){
            saleDetailDTOs.add( convertSaleDetailToDTO(saleDetail) );
        }
        return saleDetailDTOs;
    }

    private SaleDetailDTO convertSaleDetailToDTO(SaleDetail saleDetail) {
        SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
        saleDetailDTO.setId_sale(saleDetail.getId_sale());
        saleDetailDTO.setId_user(saleDetail.getId_user());
        saleDetailDTO.setTotal_price(saleDetail.getTotal_price());
        saleDetailDTO.setId_shopping_cart(saleDetail.getId_shopping_cart());
        return saleDetailDTO;
    }
}
