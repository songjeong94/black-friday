package org.example.paymentservice.service;

import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.entity.PaymentMethod;
import org.example.paymentservice.enums.PaymentMethodType;
import org.example.paymentservice.enums.PaymentStatus;
import org.example.paymentservice.pg.CreditCardPaymentAdapter;
import org.example.paymentservice.repository.PaymentMethodRepository;
import org.example.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CreditCardPaymentAdapter creditCardPaymentAdapter;

    public PaymentMethod registerPaymentMethod(
            Long userId,
            PaymentMethodType paymentMethodType,
            String creditCardNumber
    ) {
        var paymentMethod = new PaymentMethod(userId, paymentMethodType, creditCardNumber);
        return paymentMethodRepository.save(paymentMethod);
    }

    public Payment processPayment(
            Long userId,
            Long orderId,
            Long amountKRW,
            Long paymentMethodId) throws Exception {
        var paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow();
        if(paymentMethod.paymentMethodType != PaymentMethodType.CREDIT_CARD) {
            throw new Exception("Unsupported payement method type");
        }

        var refCode = creditCardPaymentAdapter.processCreditCardPayment(amountKRW, paymentMethod.creditCardNumber);

        var payment = new Payment(
                userId,
                orderId,
                amountKRW,
                paymentMethod.paymentMethodType,
                paymentMethod.creditCardNumber,
                PaymentStatus.COMPLETED,
                refCode);
        return paymentRepository.save(payment);
    }

    public PaymentMethod getPaymentMethodByUser(Long userId) {
        return paymentMethodRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow();
    }
}
