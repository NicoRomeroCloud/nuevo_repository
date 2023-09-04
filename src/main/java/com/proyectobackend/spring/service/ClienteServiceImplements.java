package com.proyectobackend.spring.service;


import com.proyectobackend.spring.dao.IClienteDao;
import com.proyectobackend.spring.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClienteServiceImplements implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    public Cliente findById(Long id){
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente){
        return clienteDao.save(cliente);
    }

    @Override
    public void delete(Long id){
        clienteDao.deleteById(id);
    }

}
