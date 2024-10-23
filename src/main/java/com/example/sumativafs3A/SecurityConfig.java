package com.example.sumativafs3A;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.sumativafs3A.model.Usuario;
import com.example.sumativafs3A.repository.UsuarioRepository;


import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Optional;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/usuarios/**").hasRole("admin") 
                                .anyRequest().authenticated() 
                )
                .httpBasic(withDefaults()); 

        return http.build(); 
    }

    @Bean
    public AuthenticationManager authManager() throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String nombre = authentication.getName(); // Usar el nombre como identificador
                String password = (String) authentication.getCredentials();

                // Busca el usuario en la base de datos
                Optional<Usuario> usuarioOptional = usuarioRepository.findByNombre(nombre);

                if (usuarioOptional.isPresent()) {
                    Usuario usuario = usuarioOptional.get();
                    if (passwordEncoder().matches(password, usuario.getPassword())) {
                        // Aquí puedes construir una autenticación válida
                        return new UsernamePasswordAuthenticationToken(usuario, password, 
                            AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol()));
                    } else {
                        throw new BadCredentialsException("Contraseña incorrecta");
                    }
                } else {
                    throw new UsernameNotFoundException("Usuario no encontrado");
                }
            }
        };
    }
}