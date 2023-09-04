package com.proyectobackend.spring.controller;

import com.proyectobackend.spring.models.entity.Cliente;
import com.proyectobackend.spring.models.entity.Role;
import com.proyectobackend.spring.models.entity.Usuario;
import com.proyectobackend.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario){

        String pass = usuario.getPassword();

        usuario.setPassword(encoder.encode(pass));

        usuario.setEnabled(true);


        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
    }

    @GetMapping("/usuarios/current-user")
    public Usuario getCurrentUser(Principal principal){

        return ((Usuario) this.usuarioService.findByUsername(principal.getName()));

    }

    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return  usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){


        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/usuarios/editar/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        Usuario usuario1 = usuarioService.findById(id);

        Usuario usuarionew = null;

        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellido(usuario.getApellido());
        usuario1.setUsername(usuario.getUsername());
        usuario1.setEmail(usuario.getEmail());

        if (usuario.getPassword().equals(usuario1.getPassword())){

            usuario1.setNombre(usuario.getNombre());
            usuario1.setApellido(usuario.getApellido());
            usuario1.setUsername(usuario.getUsername());
            usuario1.setEmail(usuario.getEmail());
            usuario1.setPassword(usuario.getPassword());
            usuarionew = usuarioService.save(usuario1);

        }else {



        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellido(usuario.getApellido());
        usuario1.setUsername(usuario.getUsername());
        usuario1.setEmail(usuario.getEmail());
        String pass = usuario.getPassword();

            usuario1.setPassword(encoder.encode(pass));



        usuarionew = usuarioService.save(usuario1);
        }
        return new ResponseEntity<>(usuarionew, HttpStatus.OK);

    }

    @DeleteMapping("/usuario/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try{
            usuarioService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el usuario");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
