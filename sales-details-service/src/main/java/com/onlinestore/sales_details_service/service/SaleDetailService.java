package com.onlinestore.sales_details_service.service;

import com.onlinestore.sales_details_service.dto.ProductDTO;
import com.onlinestore.sales_details_service.dto.SaleDetailDTO;
import com.onlinestore.sales_details_service.dto.ShoppingCartDTO;
import com.onlinestore.sales_details_service.feign.IProductAPI;
import com.onlinestore.sales_details_service.feign.IShopping_cartAPI;
import com.onlinestore.sales_details_service.model.SaleDetail;
import com.onlinestore.sales_details_service.repository.ISaleDetailRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
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
    public void createSaleDetail(SaleDetailDTO saleDetailDTO) {

        SaleDetail saleDetail = this.convertDTOtoSaleDetailEntity(saleDetailDTO);
        iSaleDetailRepository.save(saleDetail);

    }

    private SaleDetail convertDTOtoSaleDetailEntity(SaleDetailDTO saleDetailDTO) {
        SaleDetail saleDetail = new SaleDetail();

        saleDetail.setId_sale(saleDetailDTO.getId_sale());
        saleDetail.setId_user(saleDetailDTO.getId_user());
        saleDetail.setId_shopping_cart(saleDetailDTO.getId_shopping_cart());
        saleDetail.setTotal_price(saleDetailDTO.getTotal_price());

        //I'm looking for the shopping cart by ID
        //Once I have the list of products ->  I set it to "saleDetail"
        ShoppingCartDTO shoppingCartDTO = this.findShoppingCartById( saleDetailDTO.getId_shopping_cart() );
        List<ProductDTO> productList = this.findProductsByCodes( shoppingCartDTO.getProduct_codes() );
        saleDetail.setProducts_to_take(productList);

        return saleDetail;
    }

    @CircuitBreaker( name = "products-service", fallbackMethod = "fallbackFindProductsByCodes")
    @Retry(name="products-service")
    private List<ProductDTO> findProductsByCodes(List<Long> productCodes) {
        return iProductAPI.findProductsByCode(productCodes);
    }

    public List<ProductDTO> fallbackFindProductsByCodes(Throwable throwable) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("the service products is unavaible");
    }

    @CircuitBreaker( name = "shopping-carts-service", fallbackMethod = "fallbackFindShoppingCartById")
    @Retry(name="shopping-carts-service")
    private ShoppingCartDTO findShoppingCartById(Long idShoppingCart) {
        return iShoppingCartAPI.findShoppingCartById( idShoppingCart );
    }

    public ShoppingCartDTO  fallbackFindShoppingCartById(Throwable throwable) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("the service shopping-carts is unavaible");
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
