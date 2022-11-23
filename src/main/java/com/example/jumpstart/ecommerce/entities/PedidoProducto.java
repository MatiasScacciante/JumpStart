package com.example.jumpstart.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name="pedidoproductos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class PedidoProducto extends Base{
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name= "fk_producto")
    private Producto producto;

    @ManyToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name= "fk_pedido")
    private Pedido pedidos;

}
