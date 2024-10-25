package com.example.sumativafs3A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.sumativafs3A.model.*;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            // Realiza la autenticación
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    login.getNombre(),
                    login.getPassword()
                )
            );

            // Establece el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Devuelve respuesta exitosa
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } catch (BadCredentialsException e) {
            // Manejo de credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }
}
