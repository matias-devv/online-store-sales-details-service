package com.onlinestore.sales_details_service.feign;

import com.onlinestore.sales_details_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="products-service")
public interface IProductAPI {

    @GetMapping("/product/find")
    public List<ProductDTO> findProductsByCode(@RequestBody List<Long> codes);
}
