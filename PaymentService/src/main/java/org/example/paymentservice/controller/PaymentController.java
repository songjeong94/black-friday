package org.example.paymentservice.controller;

import org.example.paymentservice.dto.PaymentMethodDto;
import org.example.paymentservice.dto.ProcessPaymentDto;
import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.entity.PaymentMethod;
import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment/methods")
    public PaymentMethod registerPaymentMethod(@RequestBody PaymentMethodDto dto) {
        return paymentService.registerPaymentMethod(
                dto.userId,
                dto.paymentMethodType,
                dto.creditCardNumber
        );
    }

    @PostMapping("/payment/process-payment")
    public Payment processPayment(@RequestBody ProcessPaymentDto dto) throws Exception {
        return paymentService.processPayment(
                dto.userId,
                dto.orderId,
                dto.amountKRW,
                dto.paymentMethodId
        );
    }

    @GetMapping("/payment/users/{userId}/first-method")
    public PaymentMethod getPaymentmethod(@PathVariable Long userId) {
        return paymentService.getPaymentMethodByUser(userId);
    }

    @GetMapping("/payment/payments/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }



}
