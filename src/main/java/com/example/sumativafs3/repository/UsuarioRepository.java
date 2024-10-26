package com.example.sumativafs3.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumativafs3.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        Optional<Usuario> findByNombre(String nombre);
} 
