package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.*;
import com.example.jumpstart.ecommerce.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private FacturaService svcFactura;
    @Autowired
    private ProductoService svcProducto;
    @Autowired
    private CategoriaService svcCategoria;
    @Autowired
    private PedidoProductoService svcPedidoProducto;
    @Autowired
    private PedidoService svcPedido;
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping(value = "/")
    public String index(Model model) {
        String saludo = "Hola Thymeleaf";
        model.addAttribute("saludo", saludo);
        return "index";
    }
    @PostMapping(value = "/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String contrasena, ModelMap modelo){
        try {
            usuarioService.registrar(mail, nombre, apellido, contrasena);
            modelo.put("exito", "Registrado correctamente");
            return  "views/inicio";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            return "views/inicio";
        }
    }
    @GetMapping("/inicio")
    public String inicio(Model model, Pageable pageable) {
        try {
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            Page<Producto> productos = this.svcProducto.findAll(pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/crud")
    public String crudProducto(Model model, Pageable pageable){
        try {
            Page<Producto> productos = this.svcProducto.findAll(pageable);
            model.addAttribute("productos",productos);
            return "views/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    // para almacenar los detalles del pedidoproducto
        List<PedidoProducto> pedidoProductos = new ArrayList<PedidoProducto>();

    // datos del pedido
        Pedido pedido = new Pedido();
    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model) throws Exception {
        PedidoProducto pedidoProducto = new PedidoProducto();
        Producto producto = new Producto();
        double sumaTotal = 0;
        System.out.println(id + "" + cantidad);
        Optional<Producto> optionalProducto = svcProducto.get(id);
        System.out.println(optionalProducto);
        producto = optionalProducto.get();

        pedidoProducto.setCantidad(cantidad);
        pedidoProducto.setPrecio(producto.getPrecio());
        pedidoProducto.setNombre(producto.getTitulo());
        pedidoProducto.setTotal(producto.getPrecio() * cantidad);
        pedidoProducto.setProducto(producto);

        //validar que le producto no se añada 2 veces
        Long idProducto=producto.getId();
        boolean ingresado=pedidoProductos.stream().anyMatch(p -> p.getProducto().getId()==idProducto);

        if (!ingresado) {
            pedidoProductos.add(pedidoProducto);
        }

        sumaTotal = pedidoProductos.stream().mapToDouble(dt -> dt.getTotal()).sum();

        pedido.setTotal((float) sumaTotal);
        model.addAttribute("pedidoProductos", pedidoProductos);
        model.addAttribute("pedido", pedido);

        return "views/carrito";
    }
    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {

        model.addAttribute("pedidoProductos", pedidoProductos);
        model.addAttribute("pedido", pedido);

        //sesion
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "/views/carrito";
    }
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id, Model model) {

        // lista nueva de prodcutos
        List<PedidoProducto> pedidosNuevos = new ArrayList<PedidoProducto>();

        for (PedidoProducto detalleOrden : pedidoProductos) {
            if (detalleOrden.getProducto().getId() != id) {
                pedidosNuevos.add(detalleOrden);
            }
        }

        // poner la nueva lista con los productos restantes
        pedidoProductos = pedidosNuevos;

        double sumaTotal = 0;
        sumaTotal = pedidoProductos.stream().mapToDouble(dt -> dt.getTotal()).sum();

        pedido.setTotal((float) sumaTotal);
        model.addAttribute("pedidoProductos", pedidoProductos);
        model.addAttribute("pedido", pedido);

        return "views/carrito";
    }
    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        model.addAttribute("cart", pedidoProductos);
        model.addAttribute("orden", pedido);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }

    @GetMapping("/comprar")
    public String finalizarCompra(HttpSession http, ModelMap modelo, Pageable pageable) throws Exception {
        Factura factura = new Factura();
        Date fechaCreacion = new Date();
        Usuario usuario = (Usuario) http.getAttribute("usuariosession");
        Usuario usuario1 = usuarioService.findById(usuario.getId());
        factura.setFecha(fechaCreacion);
        factura.setTotal(pedido.getTotal());
        factura.setUsuario(usuario1);
        svcFactura.save(factura);
        pedido.setFechaFin(fechaCreacion);
        pedido.setFactura(factura);
        svcPedido.save(pedido);

        //guardar detalles
        for (PedidoProducto dt:pedidoProductos) {
            dt.setPedidos(pedido);
            svcPedidoProducto.save(dt);
        }

        ///limpiar lista y orden
        pedido = new Pedido();
        pedidoProductos.clear();

        List<Pedido> pedidos = svcPedido.pedidosFacturados(usuario1);
        modelo.addAttribute("pedidos", pedidos);
        modelo.addAttribute("usuario", usuario1);
        return "redirect:/usuarios/compras";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String titulo, Model model, Pageable pageable) throws Exception {
        log.info("Nombre del producto: {}", titulo);
        List<Producto> productos= svcProducto.findAll(pageable).stream().filter( p -> p.getTitulo().contains(titulo)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "formulario/inicio";
    }
}
