package com.example.sumativafs3A.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.sumativafs3A.model.Usuario;
import com.example.sumativafs3A.service.UsuarioService;
import org.springframework.security.config.Customizer;


import java.util.Optional;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // Inyectar el manejador de 401

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para facilitar pruebas
            .authorizeHttpRequests(auth -> auth
                //
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").authenticated()  // GET permitido para usuarios autenticados
                .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("admin") // POST permitido solo para admin
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("admin")  // PUT permitido solo para admin
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("admin") // DELETE solo para admin
                .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                // seguridad endpoint Microservicio Gestion de productos
                .requestMatchers(HttpMethod.GET, "/api/productos/**").authenticated() 
                .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("admin")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("admin") 
                .requestMatchers(HttpMethod.PUT, "/api/prductos/**").hasRole("admin")
            
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // Usar manejador personalizado para
                                                                                  // 401 Unauthorized
            )        
            .httpBasic(Customizer.withDefaults());  
    
        return http.build();
    }

    //autenticacion usuarios
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsService());

        return authenticationManagerBuilder.build();
    }

    //recuperar informacion usuario basado en el nombre
    @Bean
    public UserDetailsService userDetailsService() {
    return username -> {
        Optional<Usuario> usuarioOptional = usuarioService.obtenerUsuarioPorNombre(username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new org.springframework.security.core.userdetails.User(
                usuario.getNombre(),
                "{noop}" + usuario.getPassword(), // Indica que la contraseña no esta codificada
                AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol())
            );
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    };
    }
}