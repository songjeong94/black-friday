package org.example.orderservice.service;

import blackfriday.protobuf.EdaMessage;
import org.example.orderservice.dto.DecreaseStockCountDto;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.feign.CatalogClient;
import org.example.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CatalogClient catalogClient;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "payment_result")
    public void consumePaymentResult(byte[] message) throws Exception {
        var object = EdaMessage.PaymentResultV1.parseFrom(message);
        logger.info("[payment result] consume: {}", object);

        // 결제 정보 업데이트
        var order = orderRepository.findById(object.getOrderId()).orElseThrow();
        order.paymentId = object.getPaymentId();
        order.orderStatus = OrderStatus.DELIVERY_REQUESTED;
        orderRepository.save(order);

        var product = catalogClient.getProduct(order.productId);

        var deliveryRequest = EdaMessage.DeliveryRequestV1.newBuilder()
                .setOrderId(order.id)
                .setProductName(product.get("name").toString())
                .setProductCount(order.count)
                .setAddress(order.deliveryAddress)
                .build();

        kafkaTemplate.send("delivery_request", deliveryRequest.toByteArray());
    }

    @KafkaListener(topics = "delivery_status_update")
    public void consumeDeliveryStatusUpdate(byte[] message) throws Exception {
        var object = EdaMessage.DeliveryStatusUpdateV1.parseFrom(message);
        logger.info("[delivery status update] consume: {}", object);

        if(object.getDeliveryStatus().equals("REQUESTED")) {
            // 상품 재고 감소
            var order = orderRepository.findById(object.getOrderId()).orElseThrow();
            var decreaseStockCountDto = new DecreaseStockCountDto();
            decreaseStockCountDto.decreaseCount = order.count;
            catalogClient.decreaseStockCount(order.productId, decreaseStockCountDto);
        }
    }

}


