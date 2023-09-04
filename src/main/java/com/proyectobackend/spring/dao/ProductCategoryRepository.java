package com.proyectobackend.spring.dao;

import com.proyectobackend.spring.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// "productCategory" es el nombre de la entrada JSON Y el path es la referencia de la ruta del producto
@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{
}
