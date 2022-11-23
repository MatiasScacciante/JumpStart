package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Pedido;
import com.example.jumpstart.ecommerce.entities.PedidoProducto;
import com.example.jumpstart.ecommerce.entities.Producto;

import java.util.List;

public interface PedidoProductoService extends BaseService<PedidoProducto, Long>{
    List<PedidoProducto> searchByPedido(long fk_pedido) throws Exception;
}
