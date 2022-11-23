package com.example.jumpstart.ecommerce.repositories;

import com.example.jumpstart.ecommerce.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long>{
    @Query(value="SELECT * FROM usuario WHERE usuario.nombre LIKE %:filtro% OR usuario.apellido Like %:filtro%",
            countQuery = "SELECT count(*) FROM usuario",
            nativeQuery = true)
    Page<Usuario> search(@Param("filtro") String filtro, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.mail = :mail")
    public Usuario searchByMail(@Param("mail") String mail);
}

