package org.example.DeliveryService.dg;

import org.springframework.stereotype.Component;

@Component
public class FastDeliveryAdapter implements DeliveryAdapter {
    @Override
    public Long processDelivery(String productName, Long productCount, String address) {
        // delivery process

        return 111111L;
    }
}
