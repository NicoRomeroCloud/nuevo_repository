package com.proyectobackend.spring.service;

import com.proyectobackend.spring.models.entity.Cliente;
import com.proyectobackend.spring.models.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> findAll();
    public Role save(Role role);

}
