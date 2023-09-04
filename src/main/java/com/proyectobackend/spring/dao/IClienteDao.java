package com.proyectobackend.spring.dao;


import com.proyectobackend.spring.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

    

}
