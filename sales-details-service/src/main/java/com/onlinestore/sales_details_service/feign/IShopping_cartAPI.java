package com.onlinestore.sales_details_service.feign;

import com.onlinestore.sales_details_service.dto.ProductDTO;
import com.onlinestore.sales_details_service.dto.ShoppingCartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="shopping-carts-service")
public interface IShopping_cartAPI {

    @GetMapping("/shopping-cart/find/{id}")
    public ShoppingCartDTO findShoppingCartById(@PathVariable Long id);
}
