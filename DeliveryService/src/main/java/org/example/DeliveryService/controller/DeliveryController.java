package org.example.DeliveryService.controller;

import org.example.DeliveryService.dto.ProcessDeliveryDto;
import org.example.DeliveryService.dto.RegisterAddressDto;
import org.example.DeliveryService.entity.Delivery;
import org.example.DeliveryService.entity.UserAddress;
import org.example.DeliveryService.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/delivery/address")
    public UserAddress registerAddress(@RequestBody RegisterAddressDto dto) {
        return deliveryService.addUserAddress(
                dto.userId,
                dto.address,
                dto.alias

        );
    }

    @PostMapping("/delivery/process-delivery")
    public Delivery processDelivery(@RequestBody ProcessDeliveryDto dto) {
        return deliveryService.processDelivery(
                dto.orderId,
                dto.productName,
                dto.productCount,
                dto.address
        );
    }

    @GetMapping("/delivery/deliveries/{deliveryId}")
    public Delivery getDelivery(@PathVariable Long deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }

    @GetMapping("/delivery/address/{addressId}")
    public UserAddress getAddress(@PathVariable Long addressId) {
        return  deliveryService.getAddress(addressId);
    }

    @GetMapping("/delivery/users/{userId}/first-address")
    public UserAddress getUserAddress(@PathVariable Long userId) {
        return deliveryService.getUserAddress(userId);
    }
}
