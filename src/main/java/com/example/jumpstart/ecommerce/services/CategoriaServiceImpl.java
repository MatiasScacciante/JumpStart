package com.example.jumpstart.ecommerce.services;

import com.example.jumpstart.ecommerce.entities.Categoria;
import com.example.jumpstart.ecommerce.repositories.BaseRepository;
import com.example.jumpstart.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, Long> implements CategoriaService{
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(BaseRepository<Categoria, Long> baseRepository) {
        super(baseRepository);
    }

}
