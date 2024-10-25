package com.example.sumativafs3A.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumativafs3A.model.Producto;
import com.example.sumativafs3A.repository.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listaProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProducto(Long id){
        return productoRepository.findById(id);
    }

    public Producto guardaProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }
}
