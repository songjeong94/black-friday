package org.example.paymentservice.entity;

import jakarta.persistence.*;
import org.example.paymentservice.enums.PaymentMethodType;

@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long userId;
    public PaymentMethodType paymentMethodType;
    public String creditCardNumber;

    public PaymentMethod() {
    }

    public PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }
}
