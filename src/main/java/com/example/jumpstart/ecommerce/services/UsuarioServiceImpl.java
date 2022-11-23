package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Usuario;
import com.example.jumpstart.ecommerce.enumerations.Rol;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public void validar(String nombre, String apellido, String email, String contrasena) throws Exception{

        if (nombre == null || nombre.isEmpty()){
            throw new Exception("el nombre no puede ser nulo o estar vacio");
        }
        if (apellido == null || apellido.isEmpty()){
            throw new Exception("el apellido no puede ser nulo o estar vacio");
        }
        if (contrasena == null || contrasena.isEmpty()){
            throw new Exception("el contrasena no puede ser nulo o estar vacio");
        }
        if (email == null ||  email.isEmpty()){
            throw new Exception("el mail no puede ser nulo o estar vacio");
        }
        Usuario usuario = usuarioRepository.searchByMail(email);
        if (usuario != null){
            throw new Exception("El mail ya esta registrado");
        }
    }
    @Override
    public Page<Usuario> search(String filtro, Pageable pageable) throws Exception {
        try{
            Page<Usuario> usuarios = usuarioRepository.search(filtro, pageable);
            return usuarios;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void registrar(String mail, String nombre, String apellido, String contrasena) throws Exception {
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setContrasena(new BCryptPasswordEncoder().encode(contrasena));
        usuario.setRol(Rol.CLIENTE);
        usuarioRepository.save(usuario);
    }

    @Override
    public void validate(String nombre, String apellido, String email, String contrasena) throws Exception {
        if (nombre == null || nombre.isEmpty()){
            throw new Exception("el nombre no puede ser nulo o estar vacio");
        }
        if (apellido == null || apellido.isEmpty()){
            throw new Exception("el apellido no puede ser nulo o estar vacio");
        }
        if (contrasena == null || contrasena.isEmpty()){
            throw new Exception("el contrasena no puede ser nulo o estar vacio");
        }
        if (email == null || email.isEmpty()){
            throw new Exception("el email no puede ser nulo o estar vacio");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.searchByMail(mail);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getMail(), usuario.getContrasena(), permisos);
        } else {
            return null;
        }
    }
}
