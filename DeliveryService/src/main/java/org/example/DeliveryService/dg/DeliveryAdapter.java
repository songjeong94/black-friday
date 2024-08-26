package org.example.DeliveryService.dg;

import org.springframework.stereotype.Component;

@Component
public interface DeliveryAdapter {

    Long processDelivery(String productName, Long productCount, String address);
}
