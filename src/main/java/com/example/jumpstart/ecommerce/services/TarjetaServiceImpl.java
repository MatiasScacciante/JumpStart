package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Tarjeta;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaServiceImpl extends BaseServiceImpl<Tarjeta, Long> implements TarjetaService{

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public TarjetaServiceImpl(BaseRepository<Tarjeta, Long> baseRepository) {
        super(baseRepository);
    }

}
