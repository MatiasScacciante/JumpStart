package com.example.jumpstart.ecommerce.repositories;

import com.example.jumpstart.ecommerce.entities.PedidoProducto;
import com.example.jumpstart.ecommerce.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProductoRepository extends BaseRepository<PedidoProducto, Long>{
    @Query(value="SELECT * FROM pedidoproducto WHERE pedidoproducto.fk_factura LIKE %:numberFac%",
            countQuery = "SELECT count(*) FROM usuario",
            nativeQuery = true)
    Page<PedidoProducto> search(@Param("numberFac") String numberFac, Pageable pageable);

    @Query(value="SELECT * FROM pedidoproductos WHERE pedidoproductos.fk_pedido= :fk_pedido",
            countQuery = "SELECT count(*) FROM productos",
            nativeQuery = true)
    List<PedidoProducto> searchByPedido(@Param("fk_pedido")long fk_pedido);
}
