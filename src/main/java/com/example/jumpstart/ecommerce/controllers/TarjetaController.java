package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Tarjeta;
import com.example.jumpstart.ecommerce.services.TarjetaServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "jumpstart/v1/tarjetas")
public class TarjetaController extends BaseControllerImpl<Tarjeta, TarjetaServiceImpl> {
}
