package com.example.sumativafs3A.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "Usuarios")
public class Usuario {
    
     @Id
    @Column(name = "id")
    private Long id;

    @NotNull(message = "usuario obligatorio")
    @NotBlank(message = "No puede ingresar un user vacio")
    @Column(name= "nombre")
    private String nombre;

    @NotNull(message = "password obligatoria")
    @NotBlank(message = "No puede ingresar una password vacia")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(name= "password")
    private String password;

    @NotNull(message = "rol obligatorio")
    @NotBlank(message = "No puede ingresar un rol vacio")
    @Pattern(regexp = "^(user|admin)$", message = "El rol debe ser 'user' o 'admin'")
    @Column(name= "rol")
    private String rol;

}
