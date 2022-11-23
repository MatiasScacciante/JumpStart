package com.example.jumpstart.ecommerce.repositories;

import com.example.jumpstart.ecommerce.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long>{
    @Query(value="SELECT * FROM pedidos WHERE pedidos.fk_cliente LIKE %:fkCliente% and pedido.pagado = '0'",
            countQuery = "SELECT count(*) FROM pedidos",
            nativeQuery = true)
    Page<Pedido> searchClientFac(@Param("fkCliente") int fkCliente, Pageable pageable);

    @Query(value="SELECT * FROM pedidos WHERE pedidos.fk_usuario=:fk_usuario AND pedidos.fk_factura IS NULL",
            countQuery = "SELECT count(*) FROM pedidos",
            nativeQuery = true)
    Pedido activePedido(@Param("fk_usuario") long fk_usuario);

    @Query("SELECT p FROM Pedido p WHERE p.factura IS NOT NULL")
    public List<Pedido> pedidosConFactura();
}
