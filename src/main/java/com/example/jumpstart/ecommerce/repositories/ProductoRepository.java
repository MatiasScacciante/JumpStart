package com.example.jumpstart.ecommerce.repositories;

import com.example.jumpstart.ecommerce.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long>{
    //BUSQUEDA POR NOMBRE DE PRODUCTO
    @Query(value="SELECT * FROM productos WHERE productos.titulo LIKE %:filtro%",
            countQuery = "SELECT count(*) FROM productos",
            nativeQuery = true)
    Page<Producto> search(@Param("filtro") String filtro, Pageable pageable);


    // BUSQUEDA DE RECOMENDADOS
    @Query(value = "SELECT * FROM productos WHERE productos.recomendado = '1'",
            countQuery = "SELECT count(*) FROM producto",
            nativeQuery = true)
    Page<Producto> searchRecomended(Pageable pageable);


    //BUSQUEDA POR CATEGORIA DE PRODUCTO
    @Query(value = "SELECT * FROM productos WHERE productos.fk_categoria LIKE %:categoryid%",
            countQuery = "SELECT count(*) FROM productos",
            nativeQuery = true)
    Page<Producto> searchByCategory(@Param("categoryid")long category,Pageable pageable);


    //BUSQUEDA FILTRANDO POR RANGO DE PRECIO
    @Query(
            value = "SELECT * FROM productos WHERE productos.precio BETWEEN :pricemin AND :pricemax order by productos.precio asc",
            countQuery = "select count(*) from productos",
            nativeQuery = true)
    Page<Producto> searchByPrice(@Param("pricemin") float pricemin, @Param("pricemax") float pricemax,Pageable pageable);


    //BUSQUEDA ORDENANDO POR PRECIO DE MANERA ASCENDENTE
    @Query(
            value = "SELECT * FROM productos order by productos.precio asc" ,
            countQuery = "select count(*) from productos",
            nativeQuery = true)
    Page<Producto> orderAscPrice(Pageable pageable);

    //BUSQUEDA ORDENANDO POR PRECIO DE MANERA DESCENDENTE
    @Query(
            value = "SELECT * FROM productos order by productos.precio desc" ,
            countQuery = "select count(*) from productos",
            nativeQuery = true)
    Page<Producto> orderDescPrice(Pageable pageable);

    @Query(
            value = "SELECT * FROM productos WHERE EXISTS (SELECT * FROM pedidoproductos WHERE pedidoproductos.fk_producto=productos.id AND pedidoproductos.id IN :listaid)" ,
            countQuery = "select count(*) from productos",
            nativeQuery = true)
    Page<Producto> findByPedidoProducto(ArrayList<Long> listaid, Pageable pageable);
}
