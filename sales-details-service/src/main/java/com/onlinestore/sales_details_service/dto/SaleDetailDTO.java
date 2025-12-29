package com.onlinestore.sales_details_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDetailDTO {

    private BigDecimal total_price;

    private Long id_shopping_cart;
    private Long id_sale;
    private Long id_user;


}
