package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Categoria;
import com.example.jumpstart.ecommerce.entities.Producto;
import com.example.jumpstart.ecommerce.services.CategoriaService;
import com.example.jumpstart.ecommerce.services.ProductoService;
import com.example.jumpstart.ecommerce.services.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

@Controller
@RequestMapping(path = "/productos")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl> {
    @Autowired
    private ProductoService svcProducto;
    @Autowired
    private CategoriaService svcCategoria;

    @GetMapping("/priceasc")
    public String searchAsc(Model model, Pageable pageable){
        try {
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            Page<Producto> productos = this.svcProducto.orderAscPrice(pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/pricedesc")
    public String searchDesc(Model model, Pageable pageable){
        try {
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            Page<Producto> productos = this.svcProducto.orderDescPrice(pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/categorias/{id}")
    public String searchByCategory(Model model, @PathVariable("id")long id, Pageable pageable) {
        try {
            //Categoria
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            //Productos
            Page<Producto> productos = this.svcProducto.searchByCategory(id,pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/price")
    public String searchByPrice(Model model,@RequestParam float pricemin,@RequestParam float pricemax,Pageable pageable){
        try {
            //Categorias
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            //Productos
            Page<Producto> productos = this.svcProducto.searchByPrice(pricemin,pricemax,pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/search")
    public String search(Model model, @RequestParam String filtro, Pageable pageable){
        try {
            //Categorias
            Page<Categoria> categorias = this.svcCategoria.findAll(pageable);
            model.addAttribute("categorias", categorias);
            //Productos
            Page<Producto> productos = this.svcProducto.search(filtro,pageable);
            model.addAttribute("productos", productos);
            return "views/inicio";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/{id}/detalle")
    public String detalleProducto(Model model, @PathVariable("id") long id) {
        try {
            Producto producto = this.svcProducto.findById(id);
            model.addAttribute("producto", producto);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/formulario")
    public String formularioProducto(Model model, @PathVariable("id")long id){
        try {
            model.addAttribute("categorias",this.svcCategoria.findAll(Pageable.unpaged()));
            if(id==0){
                model.addAttribute("producto",new Producto());
            }else{
                model.addAttribute("producto",this.svcProducto.findById(id));
            }
            return "views/formulario/producto";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/formulario/{id}")
    public String guardarProducto(
            @RequestParam("archivo") MultipartFile archivo,
            @Valid @ModelAttribute("producto") Producto producto,
            BindingResult result,
            Model model,@PathVariable("id")long id
    ) {

        try {
            model.addAttribute("categorias",this.svcCategoria.findAll(Pageable.unpaged()));
            if(result.hasErrors()){
                return "views/formulario/producto";
            }
            String ruta = "C://Productos/imagenes";
            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "";
            extension = "."+archivo.getOriginalFilename().substring(index+1);
            String nombreFoto = Calendar.getInstance().getTimeInMillis()+extension;
            Path rutaAbsoluta = id != 0 ? Paths.get(ruta + "//"+producto.getImagen()) :
                    Paths.get(ruta+"//"+nombreFoto);
            if(id==0){
                if(archivo.isEmpty()){
                    model.addAttribute("errorImagenMsg","La imagen es requerida");
                    return "views/formulario/producto";
                }
                if(!this.validarExtension(archivo)){
                    model.addAttribute("errorImagenMsg","La extension no es valida");
                    return "views/formulario/producto";
                }
                if(archivo.getSize() >= 15000000){
                    model.addAttribute("errorImagenMsg","El peso excede 15MB");
                    return "views/formulario/producto";
                }
                Files.write(rutaAbsoluta,archivo.getBytes());
                producto.setImagen(nombreFoto);
                this.svcProducto.save(producto);
            }else{
                if(!archivo.isEmpty()){
                    if(!this.validarExtension(archivo)){
                        model.addAttribute("errorImagenMsg","La extension no es valida");
                        return "views/formulario/producto";
                    }
                    if(archivo.getSize() >= 15000000){
                        model.addAttribute("errorImagenMsg","El peso excede 15MB");
                        return "views/formulario/producto";
                    }
                    Files.write(rutaAbsoluta,archivo.getBytes());
                }
                this.svcProducto.update(id,producto);
            }
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/eliminar")
    public String eliminarProducto(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("producto",this.svcProducto.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/{id}/eliminar")
    public String desactivarProducto(Model model,@PathVariable("id")long id){
        try {
            this.svcProducto.delete(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo){
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
