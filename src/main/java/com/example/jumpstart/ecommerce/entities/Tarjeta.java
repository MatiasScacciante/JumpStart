package com.example.jumpstart.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="tarjetas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Tarjeta extends Base{
    @Column(name="nro_tarjeta")
    private int nro_tarjeta;

    @Column(name="codigo_seguridad")
    private int codigo_seguridad;

    @Column(name="titular")
    private String titular;

    @Column(name="vencimiento")
    private Date vencimiento;

}
