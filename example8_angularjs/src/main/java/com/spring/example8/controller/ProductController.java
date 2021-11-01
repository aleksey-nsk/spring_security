package com.spring.example8.controller;

import com.spring.example8.dto.ProductDto;
import com.spring.example8.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping
    public Page<ProductDto> findAll(@RequestParam MultiValueMap<String, String> params,
                                    @RequestParam("p") Integer pageIndex) {
        return productService.findAll(params, pageIndex);
    }
}
