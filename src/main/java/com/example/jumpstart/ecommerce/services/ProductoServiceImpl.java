package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Producto;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository) {
        super(baseRepository);
    }
    @Override
    public Page<Producto> search(String filtro, Pageable pageable) throws Exception {
        try{
            Page<Producto> productos = productoRepository.search(filtro, pageable);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Producto> searchByCategory(long id, Pageable pageable) throws Exception {
        try{
            Page<Producto> productos = productoRepository.searchByCategory(id, pageable);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Producto> searchByPrice(float pricemin, float pricemax, Pageable pageable) throws Exception {
        try{
            Page<Producto> productos = productoRepository.searchByPrice(pricemin, pricemax, pageable);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Producto> orderAscPrice(Pageable pageable) throws Exception {
        try{
            Page<Producto> productos = productoRepository.orderAscPrice(pageable);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Producto> orderDescPrice(Pageable pageable) throws Exception {
        try{
            Page<Producto> productos = productoRepository.orderDescPrice(pageable);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Page<Producto> findByPedidoProducto(ArrayList<Long> listaid, Pageable pageable) throws Exception {
        try {
            Page<Producto> productos = productoRepository.findByPedidoProducto(listaid,pageable);
            return productos;
        }   catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public Optional<Producto> get(Long id) {
        return productoRepository.findById(id);
    }
}
