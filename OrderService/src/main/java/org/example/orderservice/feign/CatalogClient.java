package org.example.orderservice.feign;

import org.example.orderservice.dto.DecreaseStockCountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Objects;

@FeignClient(name = "catalogClient", url = "http://catalog-service:8080")
public interface CatalogClient {

    @GetMapping(value = "/catalog/products/{productId}")
    Map<String, Object> getProduct(@PathVariable Long productId);

    @PostMapping(value = "/catalog/prducts/{productId}/decreaseStockCount")
    void decreaseStockCount(@PathVariable Long productId, @RequestBody DecreaseStockCountDto dto);
}
