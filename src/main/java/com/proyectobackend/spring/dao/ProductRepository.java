package com.proyectobackend.spring.dao;

import com.proyectobackend.spring.entity.Order;
import com.proyectobackend.spring.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
public interface ProductRepository extends JpaRepository<Product, Long>  {

    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);

    Page<Product> findByNameContaining(@Param("name") String name, Pageable page);

    @Query("select o from Order o")
    public List<Order> FindallActivos();
}
