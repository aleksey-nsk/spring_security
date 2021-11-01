package com.spring.example8.service;

import com.spring.example8.component.Cart;
import com.spring.example8.dto.CartDto;
import com.spring.example8.dto.CartItemDto;
import com.spring.example8.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Cart cart;
    private final ProductService productService;

    public CartDto find() {
        CartDto result = new CartDto();

        List<CartItemDto> items = cart.findAll()
                .entrySet()
                .stream()
                .map(entry -> {
                    ProductDto product = productService.findById(entry.getKey());
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setProductTitle(product.getTitle());
                    cartItemDto.setPrice(product.getPrice());
                    cartItemDto.setQuantity(entry.getValue());
                    cartItemDto.setPricePerProduct(product.getPrice());
                    cartItemDto.setPrice(entry.getValue() * product.getPrice());
                    return cartItemDto;
                })
                .collect(Collectors.toList());

        result.setItems(items);

        result.setTotalPrice(items.stream()
                .map(CartItemDto::getPrice)
                .reduce(Integer::sum)
                .orElse(0));

        return result;
    }

    public void add(Long productId) {
        cart.addProduct(productId);
    }

    public void deleteAll() {
        cart.deleteAll();
    }
}
