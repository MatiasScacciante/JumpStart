package com.example.jumpstart.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Categoria extends Base{

    private String nombre;
    private boolean activo = true;
}
