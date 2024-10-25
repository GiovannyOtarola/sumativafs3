package com.example.sumativafs3A.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sumativafs3A.service.*;

import jakarta.validation.Valid;

import com.example.sumativafs3A.exception.ResourceNotFoundException;
import com.example.sumativafs3A.model.*;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;
    

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(usuario);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetalles) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
        .orElseThrow(() -> new ResourceNotFoundException("El libro con ID " + id + " no fue encontrado."));

            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setPassword(usuarioDetalles.getPassword());
            usuario.setRol(usuarioDetalles.getRol());

            Usuario usuarioActualizado = usuarioService.guardUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID " + id + " no fue encontrado."));

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Obtener todos los usuarios 
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}
