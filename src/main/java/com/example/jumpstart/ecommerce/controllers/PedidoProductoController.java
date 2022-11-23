package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Categoria;
import com.example.jumpstart.ecommerce.entities.Pedido;
import com.example.jumpstart.ecommerce.entities.PedidoProducto;
import com.example.jumpstart.ecommerce.entities.Producto;
import com.example.jumpstart.ecommerce.services.PedidoProductoService;
import com.example.jumpstart.ecommerce.services.PedidoProductoServiceImpl;
import com.example.jumpstart.ecommerce.services.PedidoService;
import com.example.jumpstart.ecommerce.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedidosproductos")
public class PedidoProductoController extends BaseControllerImpl<PedidoProducto, PedidoProductoServiceImpl> {
    @Autowired
    private PedidoService svcPedido;
    @Autowired
    private ProductoService svcProducto;
    @Autowired
    private PedidoProductoService svcPedidoProducto;

    /*@GetMapping("usuarios/{id}/agregar_producto")
    public String agregarproducto(Model model,@PathVariable("id")long id, long productoid, int cant_producto){
        try {
            Producto producto = this.svcProducto.findById(productoid);
            Pedido pedido = this.svcPedido.activePedido(id);
            model.addAttribute("pedido", pedido);
            PedidoProducto pedidoProducto = this.svcPedidoProducto.save(new PedidoProducto(cant_producto,producto,pedido));
            return "views/inicio";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }*/
}
