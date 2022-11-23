package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends BaseService<Usuario, Long>, UserDetailsService {
    Page<Usuario> search(String filtro, Pageable pageable) throws Exception;
    void registrar(String mail, String nombre, String apellido, String contrasena) throws Exception;
    public void validate(String nombre, String apellido, String email, String contrasena) throws Exception;
    public void validar(String nombre, String apellido, String email, String contrasena) throws Exception;
}
