package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Pedido;
import com.example.jumpstart.ecommerce.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService extends BaseService<Pedido, Long>{
    Page<Pedido> searchClientFac(int fkCliente, Pageable pageable) throws Exception;
    Pedido activePedido(long fk_cliente) throws Exception;

    List<Pedido> pedidosFacturados(Usuario usuario) throws Exception;
}
