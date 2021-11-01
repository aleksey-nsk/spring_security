package com.spring.example8.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private String productTitle;
    private Integer quantity;
    private Integer pricePerProduct;
    private Integer price;
}
