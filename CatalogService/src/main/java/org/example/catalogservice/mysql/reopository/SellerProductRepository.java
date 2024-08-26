package org.example.catalogservice.mysql.reopository;


import org.example.catalogservice.mysql.entity.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {
    List<SellerProduct> findBySellerId(Long sellerId);
}
