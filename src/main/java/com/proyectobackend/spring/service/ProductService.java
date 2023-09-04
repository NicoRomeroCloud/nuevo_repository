package com.proyectobackend.spring.service;

import com.proyectobackend.spring.entity.Order;
import com.proyectobackend.spring.entity.Product;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public Product save(Product product);

    public Product findById(Long id);

    public List<Product> findAll();



    public void delete(Long id);



}
