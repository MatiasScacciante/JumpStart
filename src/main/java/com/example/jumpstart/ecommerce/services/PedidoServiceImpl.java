package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Pedido;

import com.example.jumpstart.ecommerce.entities.Usuario;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido, Long> implements PedidoService{
        @Autowired
        private PedidoRepository pedidoRepository;

    public PedidoServiceImpl(BaseRepository<Pedido, Long> baseRepository) {
            super(baseRepository);
        }
    @Override
    public Page<Pedido> searchClientFac(int fkCliente, Pageable pageable) throws Exception {
        try {
            Page<Pedido> pedidos = pedidoRepository.searchClientFac(fkCliente, pageable);
            return pedidos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido activePedido(long fk_cliente) throws Exception{
        Pedido pedido = pedidoRepository.activePedido(fk_cliente);
        return pedido;
    }


    @Override
    public List<Pedido> pedidosFacturados(Usuario usuario) throws Exception {
        List<Pedido> pedidos = pedidoRepository.pedidosConFactura(usuario);
        return pedidos;
    }
}
