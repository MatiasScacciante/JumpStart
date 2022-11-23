package com.example.jumpstart.ecommerce.controllers;

import com.example.jumpstart.ecommerce.entities.Domicilio;
import com.example.jumpstart.ecommerce.services.DomicilioServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "jumpstart/v1/domicilios")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl>{
}
