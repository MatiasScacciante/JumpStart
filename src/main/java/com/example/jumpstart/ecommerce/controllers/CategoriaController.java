package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Categoria;
import com.example.jumpstart.ecommerce.services.CategoriaServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "jumpstart/v1/categorias")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl>{
}
