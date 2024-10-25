package com.example.sumativafs3A.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.sumativafs3A.exception.ResourceNotFoundException;
import com.example.sumativafs3A.model.Producto;
import com.example.sumativafs3A.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@Validated
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    // Crear un nuevo producto con validación
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardaProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED); // Devolver 201 Created
    }

    // Obtener un producto por su ID con manejo de excepción
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(producto);
    }

    // Eliminar un producto por su ID con manejo de excepción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // Verifica si el producto existe, si no, lanza la excepción
        productoService.obtenerProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con ID " + id + " no fue encontrado."));

        // Elimina el producto si existe
        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Actualizar un producto existente con manejo de excepción
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto detallesProducto) {
        Producto producto = productoService.obtenerProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con ID " + id + " no fue encontrado."));

        producto.setNombre(detallesProducto.getNombre());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setPrecio(detallesProducto.getPrecio());
        
        Producto productoActualizado = productoService.guardaProducto(producto);
        return ResponseEntity.ok(productoActualizado);
    }
}
