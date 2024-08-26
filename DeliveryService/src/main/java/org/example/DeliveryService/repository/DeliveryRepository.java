package org.example.DeliveryService.repository;

import org.example.DeliveryService.entity.Delivery;
import org.example.DeliveryService.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByOrderId(Long orderId);
    List<Delivery> findAllByStatus(DeliveryStatus deliveryStatus);
}
