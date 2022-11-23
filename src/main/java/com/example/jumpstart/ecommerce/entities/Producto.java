package com.example.jumpstart.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name="productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Producto extends Base{
    @NotEmpty(message = "No puede ser nulo la fecha")
    private String titulo;
    @Size(min=5,max=100,message= "La descripcion debe ser entre 5 y 100 caracteres")
    private String descripcion;

    private String imagen;

    @Min(value = 5,message="El precio debe tener un minimo de 5")
    @Max(value = 10000, message="El precio debe ser menor a 1000")
    private float precio;

    @Min(value = 1,message="El stock debe tener un minimo de 5")
    @Max(value = 10000, message="El stock debe ser menor a 1000")
    private short stock;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="No puede ser nulo la fecha")
    @PastOrPresent(message="Debe ser igual o menor a la fecha de hoy")
    private Date fechaLanzamiento;

   /* @NotNull(message="Es requerido el estudio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_estudio", nullable = false)
    private Estudio estudio;*/

    @NotNull(message="Es requerida la categoria")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;
}
