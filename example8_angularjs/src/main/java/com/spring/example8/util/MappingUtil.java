package com.spring.example8.util;

import com.spring.example8.dto.ProductDto;
import com.spring.example8.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MappingUtil {

    public static ProductDto modelToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
