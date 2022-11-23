package com.example.jumpstart.ecommerce.repositories;


import com.example.jumpstart.ecommerce.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long>{
    @Query(value="SELECT * FROM categoria WHERE categoria.nombre LIKE %:filtro%",
            countQuery = "SELECT count(*) FROM cliente",
            nativeQuery = true)
    Page<Categoria> searchForCategory(@Param("filtro") String filtro, Pageable pageable);
}
