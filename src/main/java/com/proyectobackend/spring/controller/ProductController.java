package com.proyectobackend.spring.controller;

import com.proyectobackend.spring.dao.OrderRepository;
import com.proyectobackend.spring.entity.Order;
import com.proyectobackend.spring.entity.Product;
import com.proyectobackend.spring.entity.ProductCategory;
import com.proyectobackend.spring.service.CategoryService;
import com.proyectobackend.spring.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductController {



    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    private HttpServletRequest request;

    private final Logger log = LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/uploads")
    public ResponseEntity<?> uploadFile(@RequestParam("archivo")MultipartFile multipartFile, @RequestParam("id") Long id){

        Map<String, Object> response = new HashMap<>();

        Product product = productService.findById(id);

        if (!multipartFile.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString()+ "_" +multipartFile.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get("C:/Users/Bolt/Desktop/plantas/subida1").resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo.toString());
            try {
                Files.copy(multipartFile.getInputStream(), rutaArchivo);
            }catch (IOException e){
                response.put("mensaje", "Error al subir la imagen");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAntes = product.getUrl();

            if (nombreFotoAntes != null && nombreFotoAntes.length() > 0){
                Path rutaFotoAntes = Paths.get("C:/Users/Bolt/Desktop/plantas/subida1").resolve(nombreFotoAntes).toAbsolutePath();
                File archivoFotoAntes = rutaFotoAntes.toFile();
                if (archivoFotoAntes.exists() && archivoFotoAntes.canRead()){
                    archivoFotoAntes.delete();
                }

            }

            product.setUrl(nombreArchivo);

            productService.save(product);

            response.put("producto", product);
            response.put("mensaje", "Has subido correctamente el pdf");

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


    }

    @GetMapping("/uploads/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get("C:/Users/Bolt/Desktop/plantas/subida1").resolve(nombreFoto).toAbsolutePath();
        log.info(rutaArchivo.toString());
        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if (!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = Paths.get("src/main/resources/static/images").resolve("not-usuario.png").toAbsolutePath();
            try {
                recurso = new UrlResource(rutaArchivo.toUri());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }

            log.error("Error, no se pudo cargar la imagen: "+nombreFoto);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Product>> listarProductos(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/listar/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Product product = null;
        Map<String, Object> response = new HashMap<>();

        try {
            product = productService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            return new ResponseEntity< Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (product == null){
            response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }


    @GetMapping("/categorias")
    public ResponseEntity<List<ProductCategory>> listarCat(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> productoId(@PathVariable Long id){
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Product> crearProducto(@RequestBody Product product){
        product.setDateCreated(new Date());
        product.setLastUpdated(new Date());
        product.setActive(true);
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Long id, @RequestBody Product product){
        product.setLastUpdated(new Date());
        product.setActive(true);
        Product product1 = productService.findById(id);

        if (product1==null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        try {
            product1.setName(product.getName());
            product1.setDescription(product.getDescription());
            product1.setSku(product.getSku());
            product1.setActive(product.isActive());
            product1.setImageUrl(product.getImageUrl());
            product1.setCategory(product.getCategory());
            product1.setUnitPrice(product.getUnitPrice());
            product1.setUnitsInStock(product.getUnitsInStock());
            product1.setUrl(product.getUrl());

            return new ResponseEntity<>(productService.save(product1), HttpStatus.CREATED);

        }catch (DataAccessException e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
