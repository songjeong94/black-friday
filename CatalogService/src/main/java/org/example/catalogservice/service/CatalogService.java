package org.example.catalogservice.service;

import org.example.catalogservice.cassandra.entity.Product;
import org.example.catalogservice.cassandra.reopository.ProductRepository;
import org.example.catalogservice.dto.ProductTagsDto;
import org.example.catalogservice.feign.SearchClient;
import org.example.catalogservice.mysql.entity.SellerProduct;
import org.example.catalogservice.mysql.reopository.SellerProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    @Autowired
    SellerProductRepository sellerProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SearchClient searchClient;

    public Product registerProduct(
            Long sellerId,
            String name,
            String description,
            Long price,
            Long stockCount,
            List<String> tags
    ) {
        var sellerProduct = new SellerProduct(sellerId);
        sellerProductRepository.save(sellerProduct);

        Product product = new Product(
                sellerProduct.id,
                sellerId,
                name,
                description,
                price,
                stockCount,
                tags
        );

        var dto = new ProductTagsDto();
        dto.tags = tags;
        dto.productId = product.id;;

        searchClient.addTagCache(dto);

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId);
        if(product.isPresent()) {
            var dto = new ProductTagsDto();
            dto.tags = product.get().tags;
            dto.productId = product.get().id;

            searchClient.removeTagCache(dto);
        }
        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);
    }

    public List<Product> getProductsBySellerId(Long sellerId) {
        var sellerProducts = sellerProductRepository.findBySellerId(sellerId);

        var products = new ArrayList<Product>();

        for(var item: sellerProducts) {
            var product = productRepository.findById(item.id);
            product.ifPresent(products::add);
        }

        return products;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Product decreaseStockCount(Long productId, Long decreaseCount) {
        var product = productRepository.findById(productId).orElseThrow();
        product.stockCount = product.stockCount - decreaseCount;

        return productRepository.save(product);
    }
}
