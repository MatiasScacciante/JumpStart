package com.example.jumpstart.ecommerce.repositories;

import com.example.jumpstart.ecommerce.entities.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends BaseRepository<Factura, Long>{
    //BUSQUEDA DE LAS FACTURAS RELACIONADAS A UN CLIENTE
    @Query(value="SELECT * FROM factura WHERE factura.fk_cliente LIKE %:fkCliente%  ORDER BY factura.fecha desc",
            countQuery = "SELECT count(*) FROM producto",
            nativeQuery = true)
    Page<Factura> searchClientFac(@Param("fkCliente") int fkCliente, Pageable pageable);
}
