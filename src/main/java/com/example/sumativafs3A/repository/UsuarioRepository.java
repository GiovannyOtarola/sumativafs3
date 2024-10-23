package com.example.sumativafs3A.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumativafs3A.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);
} 
