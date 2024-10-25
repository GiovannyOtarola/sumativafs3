package com.example.sumativafs3A.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;

import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column (name = "nombre")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    @Column (name = "descripcion")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column (name = "precio")
    private Double precio;
    
}
