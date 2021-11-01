package com.spring.example8.service;

import com.spring.example8.dto.ProductDto;
import com.spring.example8.exception.ProductNotFoundException;
import com.spring.example8.repository.ProductRepository;
import com.spring.example8.util.MappingUtil;
import com.spring.example8.util.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final static int PAGE_SIZE = 4; // будем отображать по 4 товара

    private final ProductRepository productRepository;

    public Page<ProductDto> findAll(MultiValueMap<String, String> params, Integer pageIndex) {
        return productRepository
                .findAll(ProductSpecifications.build(params), PageRequest.of(pageIndex - 1, PAGE_SIZE))
                .map(it -> MappingUtil.modelToDto(it));
    }

    public ProductDto findById(Long id) {
        log.debug("Найти продукт с идентификатором: " + id);
        return productRepository.findById(id)
                .map(it -> MappingUtil.modelToDto(it))
                .orElseThrow(() -> new ProductNotFoundException());
    }
}
