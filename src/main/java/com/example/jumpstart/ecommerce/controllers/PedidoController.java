package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Pedido;
import com.example.jumpstart.ecommerce.entities.PedidoProducto;
import com.example.jumpstart.ecommerce.entities.Producto;
import com.example.jumpstart.ecommerce.entities.Usuario;
import com.example.jumpstart.ecommerce.services.PedidoProductoService;
import com.example.jumpstart.ecommerce.services.PedidoService;
import com.example.jumpstart.ecommerce.services.PedidoServiceImpl;
import com.example.jumpstart.ecommerce.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "pedidos")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{

    @Autowired
    PedidoService pedidoService;

    @Autowired
    private PedidoService svcPedido;

    @Autowired
    private ProductoService svcProducto;

    @Autowired
    private PedidoProductoService svcPedidoProducto;

    @GetMapping("/searchClientFac")
    public ResponseEntity<?> searchClientFac(@RequestParam int fkCliente, Pageable pageable){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchClientFac(fkCliente,pageable));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \""+ e.getMessage() + "\"}"));
        }
    }
    @GetMapping("usuarios/{id}")
    public String carrito(Model model, @PathVariable("id")long id, Pageable pageable) {
        try {
            //Pedido
            Pedido pedido = this.svcPedido.activePedido(id);
            model.addAttribute("pedido", pedido);
            //Productos
            List<PedidoProducto> pedidoproductos = this.svcPedidoProducto.searchByPedido(pedido.getId());
            model.addAttribute("pedidoproductos", pedidoproductos);
            ArrayList<Long> listaid = new ArrayList<Long>();
            for (PedidoProducto pedidoproducto :pedidoproductos) {
                listaid.add(pedidoproducto.getId());
            }
            String listaidString = listaid.stream().map(Object::toString).collect(Collectors.joining(", "));
            Page<Producto> productos = this.svcProducto.findByPedidoProducto(listaid,pageable);
            model.addAttribute("productos", productos);
            return "views/carrito";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/facturas")
    public String perfil(HttpSession http, ModelMap modelo){
        Usuario logueado = (Usuario) http.getAttribute("usuariosession");
        try{
            modelo.put("usuario", logueado);
            modelo.addAttribute("pedidos", pedidoService.pedidosFacturados());
        }catch (Exception e){
            modelo.put("error", e.getMessage());
        }finally {
            return "views/facturas";
        }

    }
}
