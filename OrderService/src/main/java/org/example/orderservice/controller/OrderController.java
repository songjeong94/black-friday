package org.example.orderservice.controller;

import org.example.orderservice.dto.FinishOrderDto;
import org.example.orderservice.dto.ProductOrderDetailDto;
import org.example.orderservice.dto.StartOrderDto;
import org.example.orderservice.dto.StartOrderResponseDto;
import org.example.orderservice.entity.ProductOrder;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/start-order")
    public StartOrderResponseDto startOrder(@RequestBody StartOrderDto dto) {
        return orderService.startOrder(dto.userId, dto.productId, dto.count);
    }

    @PostMapping("/order/finish-order")
    public ProductOrder finishOrder(@RequestBody FinishOrderDto dto) {
        return orderService.finishOrder(dto.orderId, dto.paymentMethodId, dto.addressId);
    }

    @GetMapping("/order/users/{userId}/orders")
    public List<ProductOrder> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/order/orders/{orderId}")
    public ProductOrderDetailDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

}
