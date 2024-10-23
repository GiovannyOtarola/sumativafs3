package com.example.sumativafs3A.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.sumativafs3A.model.Usuario;
import com.example.sumativafs3A.repository.UsuarioRepository;

public class UsuarioService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar la contraseña
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            if (!usuario.getPassword().equals(usuarioExistente.get().getPassword())) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

}
