package com.example.jumpstart.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Pedido extends Base{
    @Column(name="Monto")
    private float total;

    @Column(name="fechaInicio")
    private Date fechaInicio;

    @Column(name="fechaFin")
    private Date fechaFin;

    @Column(name="pagado")
    private Boolean pagado;

    //@ManyToOne(cascade = CascadeType.REFRESH)
    //@JoinColumn(name = "fk_usuario")
    //private Usuario usuario;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_domicilio")
    private Domicilio domicilio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_factura")
    private Factura factura;
}
