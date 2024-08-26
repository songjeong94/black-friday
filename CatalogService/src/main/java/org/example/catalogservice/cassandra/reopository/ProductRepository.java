package org.example.catalogservice.cassandra.reopository;

import org.example.catalogservice.cassandra.entity.Product;
import org.example.catalogservice.mysql.entity.SellerProduct;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {
    List<SellerProduct> findBySellerId(Long sellerId);
}
