package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Factura;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    public FacturaServiceImpl(BaseRepository<Factura, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public Page<Factura> searchClientFac(int fkCliente, Pageable pageable) throws Exception {
        try{
            Page<Factura> facturas = facturaRepository.searchClientFac(fkCliente, pageable);
            return facturas;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
