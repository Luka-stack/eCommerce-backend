package com.nisshoku.ecommerce.dao;

import com.nisshoku.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(@RequestParam Long id, Pageable pageable);

    Page<Product> findByNameContaining(@RequestParam String name, Pageable pageable);
}
