package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface ProductoService extends BaseService<Producto, Long>{
    Page<Producto> search(String filtro, Pageable pageable) throws Exception;

    Page<Producto> searchByCategory(long id, Pageable pageable) throws Exception;

    Page<Producto> searchByPrice(float pricemin, float pricemax,Pageable pageable) throws Exception;

    Page<Producto> orderAscPrice(Pageable pageable) throws Exception;

    Page<Producto> orderDescPrice(Pageable pageable) throws Exception;

    Page<Producto> findByPedidoProducto(ArrayList<Long> listaid, Pageable pageable) throws Exception;

    Optional<Producto> get(Long id);
}
