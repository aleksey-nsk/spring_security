package com.spring.example8.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

// Вся корзина будет храниться в сессионном бине
@Component
@Scope(scopeName = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class Cart {

    private Map<Long, Integer> products;

    public Cart() {
        this.products = new HashMap<>();
    }

    public Map<Long, Integer> findAll() {
        return Collections.unmodifiableMap(products);
    }

    public void addProduct(Long productId) {
        products.merge(productId, 1, (o1, o2) -> o1 + o2);
    }

    public void deleteAll() {
        products.clear();
    }
}
