package org.example.catalogservice.controller;

import org.example.catalogservice.cassandra.entity.Product;
import org.example.catalogservice.dto.DecreaseStockCountDto;
import org.example.catalogservice.dto.RegisterProductDto;
import org.example.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @PostMapping("/catalog/products")
    public Product registerProduct(@RequestBody RegisterProductDto dto) {
        return catalogService.registerProduct(
                dto.sellerId,
                dto.name,
                dto.description,
                dto.price,
                dto.stockCount,
                dto.tags
        );
    }

    @DeleteMapping("/catalog/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/catalog/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return catalogService.getProductById(productId);
    }

    @GetMapping("/catalog/sellers/{sellerId}/products")
    public List<Product> getProductsBySellerId(@PathVariable Long sellerId) {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @PostMapping("/catalog/products/{productId}/decreaseStockCount")
    public Product decreaseStockCOunt(@PathVariable Long productId, @RequestBody DecreaseStockCountDto dto) {
        return catalogService.decreaseStockCount(productId, dto.decreaseCount);
    }
}
