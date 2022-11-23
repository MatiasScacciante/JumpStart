package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Categoria;
import com.example.jumpstart.ecommerce.entities.Producto;
import com.example.jumpstart.ecommerce.entities.Usuario;
import com.example.jumpstart.ecommerce.services.PedidoService;
import com.example.jumpstart.ecommerce.services.UsuarioService;
import com.example.jumpstart.ecommerce.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "usuarios")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl>{

    @Autowired
    PedidoService pedidoService;

    @Autowired
    UsuarioService svcUsuario;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filtro, Pageable pageable){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(filtro,pageable));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \""+ e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/compras")
    public String perfil(HttpSession http, ModelMap modelo){
        Usuario logueado = (Usuario) http.getAttribute("usuariosession");
        try{
            modelo.put("usuario", logueado);
            modelo.addAttribute("pedidos", pedidoService.pedidosFacturados());
        }catch (Exception e){
            modelo.put("error", e.getMessage());
        }finally {
            return "views/perfil";
        }

    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession http) {
        Usuario logueado = (Usuario) http.getAttribute("usuariosession");
        try{
            modelo.put("usuario", logueado);
            modelo.addAttribute("pedidos", pedidoService.pedidosFacturados());
        }catch (Exception e){
            modelo.put("error", e.getMessage());
        }finally {
            return "views/perfil";
        }
    }

    @GetMapping("/formulario")
    public String formularioProducto(ModelMap modelo, HttpSession http){
        Usuario logueado = (Usuario) http.getAttribute("usuariosession");
        try{
            modelo.put("usuario", logueado);
            return "views/formulario/usuario";
        }catch(Exception e){
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/{id}")
    public String guardarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            Model model,HttpSession http
    ) {
        Usuario logueado = (Usuario) http.getAttribute("usuariosession");
        try {
            this.svcUsuario.update(logueado.getId(),usuario);
            return "redirect:/perfil";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
