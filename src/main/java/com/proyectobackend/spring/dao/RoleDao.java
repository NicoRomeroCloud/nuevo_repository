package com.proyectobackend.spring.dao;

import com.proyectobackend.spring.entity.Product;
import com.proyectobackend.spring.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface RoleDao extends JpaRepository<Role, Long>  {



}
