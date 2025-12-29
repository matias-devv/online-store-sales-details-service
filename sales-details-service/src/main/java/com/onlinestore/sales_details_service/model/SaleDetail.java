package com.onlinestore.sales_details_service.model;

import com.onlinestore.sales_details_service.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter
@Setter
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sale_detail;

    private Long id_user;
    private Long id_sale;
    private Long id_shopping_cart;
    private BigDecimal total_price;

    //It has a product list because I need to freeze the chosen products and the quantity to take.
    @ElementCollection
    private List<ProductDTO> products_to_take;

}
