package com.proyectobackend.spring.service;

import com.proyectobackend.spring.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    public List<ProductCategory> findAll();

}
