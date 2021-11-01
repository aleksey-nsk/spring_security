package com.spring.example8.controller;

import com.spring.example8.dto.CartDto;
import com.spring.example8.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDto find() {
        log.debug("CartController -> method find()");
        return cartService.find();
    }

    @PutMapping
    public void add(@RequestParam Long productId) {
        log.debug("CartController -> method add()");
        log.debug("  productId: " + productId);
        cartService.add(productId);
    }

    @DeleteMapping
    public void clear() {
        cartService.deleteAll();
    }
}
