package com.example.factorio.service;

import com.example.factorio.model.Order;
import com.example.factorio.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order){
        return orderRepository.saveAndFlush(order);
    }
}
