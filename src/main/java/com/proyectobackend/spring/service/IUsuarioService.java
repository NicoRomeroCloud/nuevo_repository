package com.proyectobackend.spring.service;

import com.proyectobackend.spring.models.entity.Cliente;
import com.proyectobackend.spring.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    public Usuario findByUsername(String username);

    public Usuario save(Usuario usuario);

    public Usuario findById(Long id);

    public List<Usuario> findAll();

    public void deleteById(Long id);

}
